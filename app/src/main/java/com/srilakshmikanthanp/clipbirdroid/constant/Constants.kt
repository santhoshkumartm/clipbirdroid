package com.srilakshmikanthanp.clipbirdroid.constant

import android.os.Build
import com.srilakshmikanthanp.clipbirdroid.BuildConfig

/**
 * @brief Get the Application Version
 * @return string
 */
fun appMajorVersion(): String {
  return BuildConfig.VERSION_NAME.split(".")[0]
}

/**
 * @brief Get the Application Version
 * @return string
 */
fun appMinorVersion(): String {
  return BuildConfig.VERSION_NAME.split(".")[1]
}

/**
 * @brief Get the Application Version
 * @return string
 */
fun appPatchVersion(): String {
  return BuildConfig.VERSION_NAME.split(".")[2]
}

/**
 * @brief Get the Application Name
 * @return string
 */
fun appName(): String {
  return BuildConfig.APP_NAME
}

/**
 * @brief Get the App Home Page
 * @return string
 */
fun appHomePage(): String {
  return BuildConfig.APP_HOME
}

/**
 * @brief Get the App ISSUES Page
 * @return string
 */
fun appIssuesPage(): String {
  return BuildConfig.APP_ISSUE
}

/**
 * @brief Get the MDns Service Name
 * @return string
 */
fun appMdnsServiceName(): String {
  return Build.HOST
}

/**
 * @brief Get the MDns Service Type
 *
 * @return string
 */
fun appMdnsServiceType(): String {
  return "_clipbird._tcp"
}

/**
 * @brief Get the App Org Name object
 *
 * @return string
 */
fun appOrgName(): String {
  return BuildConfig.APP_ORG
}
