// pipeline {
//     agent any

//     environment {
//         SERVICE_ID = "srv-xxxxxxxxxxxx"   // Replace with your Render Service ID
//     }

//     stages {

//         stage('Clone Repository') {
//             steps {
//                 git branch: 'main',
//                     url: 'https://github.com/Delta-Student27/ForYou.git'
//             }
//         }

//         stage('Deploy to Render') {
//             steps {
//                 withCredentials([string(credentialsId: 'RENDER_API_KEY', variable: 'RENDER_KEY')]) {
//                     bat """
//                     curl -X POST https://api.render.com/v1/services/%SERVICE_ID%/deploys ^
//                     -H "Authorization: Bearer %RENDER_KEY%" ^
//                     -H "Content-Type: application/json"
//                     """
//                 }
//             }
//         }
//     }

//     post {
//         success {
//             echo 'Deployment triggered successfully 🚀'
//         }
//         failure {
//             echo 'Deployment failed ❌'
//         }
//     }
// }