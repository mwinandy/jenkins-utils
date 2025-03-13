def call(String status, String jobName, String buildNumber, String buildUrl) {
    withCredentials([
        string(credentialsId: 'TELEGRAM_BOT_TOKEN', variable: 'BOT_TOKEN'),
        string(credentialsId: 'TELEGRAM_CHAT_ID', variable: 'CHAT_ID'),
        string(credentialsId: 'TELEGRAM_THREAD_ID', variable: 'THREAD_ID')
    ]) {
        def message = ""
        def silent = ""

        if (status == "SUCCESS") {
            message = "✅ *Build Success!* 🚀\n📌 *Job:* `${jobName}`\n🔢 *Build Number:* `${buildNumber}`\n[🔗 View Build](${buildUrl})"
            silent = "-d disable_notification=true"
        } else {
            message = "❌ *Build Failed!* ⚠️\n📌 *Job:* `${jobName}`\n🔢 *Build Number:* `${buildNumber}`\n[🔗 View Logs](${buildUrl}/console)"
        }

        sh "curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendMessage -d chat_id=${CHAT_ID} -d message_thread_id=${THREAD_ID} -d parse_mode=Markdown ${silent} -d text='${message}'"
    }
}
