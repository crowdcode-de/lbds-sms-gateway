pipeline {
    agent none
    options {
        office365ConnectorWebhooks([[
                                            offsetNotification: true,
                                            notifySuccess: true,
                                            notifyFailure: true,
                                            notifyUnstable: true,
                                            notifyBackToNormal: true,
                                            url: "${env.TEAMS_LBDS_WEBHOOK_URL}"
                                    ]]
        )
    }
    stages {
        stage('Prepare') {
            agent { label 'master' }
            steps {
                withCredentials(
                        [usernamePassword(credentialsId: 'crowdcodeBitbucket',
                                usernameVariable: 'gitUser',
                                passwordVariable: 'gitPwd'
                        )]) {
                    script {
                        sh "git status"
                        sh "git checkout ${env.BRANCH_NAME}"
                        sh "git reset --hard origin/${env.BRANCH_NAME}"
                        pom = readMavenPom file: 'pom.xml'
                        final orginalVersion = pom.version
                        mvn("-DfailOnMissingBranchId=false -Dnamespace=org.hzi -DbranchName=${env.BRANCH_NAME} -Dgituser=${gituser} -Dgitpassword=${gitPwd} io.crowdcode:bgav-maven-plugin:1.1.0:bgav")
                        pom = readMavenPom file: 'pom.xml'
                        final newVersion = pom.version



                        if (!orginalVersion.equals(newVersion)) {
                            sh "mkdir -p target && touch target/DO_NOT_BUILD"
                            env.DO_NOT_BUILD=true
                        } else {
                            env.DO_NOT_BUILD=false
                        }

//                        if (!newVersion.equals(newAarVersion)) {
//                            mvn("versions:set -DnewVersion="+newVersion+" -f pom-aar.xml")
//
//                            sh '''
//                                git config --global user.email "jenkins@crowdcode.io"
//                                git config --global user.name "Jenkins User"
//                                git add pom.xml pom-aar.xml
//                                git commit -m'aligned pom versions'
//                            '''
//                            sh "git push ${env.GIT_URL} ${env.BRANCH_NAME}"
//
//                            env.DO_NOT_BUILD=true
//                        }

                    }
                }
            }
        }
        stage('Build') {
            agent { label 'master' }
            when {  environment name: "DO_NOT_BUILD", value: "false" }
            steps {  mvn("clean install -DskipTests=true") }
        }
        stage('Unit tests') {
            agent { label 'master' }
            when {  environment name: "DO_NOT_BUILD", value: "false" }
            steps { mvn("test -P-checks,test-coverage -Dskip.unit.tests=false -Dskip.integration.tests=true") }
        }
        stage('Integration tests') {
            agent { label 'master' }
            when {  environment name: "DO_NOT_BUILD", value: "false" }
            steps { mvn("verify -P-checks,test-coverage -Dskip.unit.tests=true -Dskip.integration.tests=false") }
        }
        stage('Deploy') {
            agent { label 'master' }
            when {  environment name: "DO_NOT_BUILD", value: "false" }
            steps { mvn("deploy -P-checks -DskipTests=true ") }
        }
        /* stage('build and deploy docker') {
            agent { label 'master' }
            steps {
                mvn("clean process-resources")
                mvn("-f target/docker/build-docker-pom.xml clean  process-resources dockerfile:build ")
                mvn("-f target/docker/build-docker-pom.xml dockerfile:push ")
            }
        }
        stage('trigger jobs') {
            steps {
               build job: "../hsmrt-deployment/${env.BRANCH_NAME.replaceAll('\\/','%2F')}", wait: false, propagate: false
            }
        } */
    }
}
def mvn(param) {
  if ( readMavenPom().getVersion().contains("SNAPSHOT") ) {
     env.docker_registry = "docker-snapshots.crowdcode.io"
  }
  else
  {
     env.docker_registry = "docker-release.crowdcode.io"
  }
  env.docker_registry_read = "docker-repo.crowdcode.io"
  withMaven(
      // globalMavenSettingsConfig: 'GlobalSettingsNexus',
      options: [openTasksPublisher(disabled: true)],
      mavenOpts: '-Xmx1536m -Xms512m',
      maven: 'maven-3.6.0') {
	    sh "mvn -U -B -e ${param} "
      }
}
