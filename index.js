import { DeviceEventEmitter, NativeModules, Platform } from "react-native";

const JVerificationModule = NativeModules.JVerificationModule;

export default class JVerification {
  /**
   * 初始化 JVerification SDK
   * @param {Object} params = {
   *  appKey: String, // 极光官网注册的 appkey
   *  channel: String, // channel 发布渠道. 可选，默认为空
   *  advertisingId: String, // advertisingIdentifier 广告标识符（IDFA). 可选，默认为空
   *  isProduction: boolean // isProduction 是否生产环境. 如果为开发状态,设置为NO;如果为生产状态,应改为YES.可选，默认为NO
   * }
   */
  static init(params) {
    JVerificationModule.setup(params);
  }

  /**
   * 获取手机号校验token
   * @param {Function} callback = (result) => {
   *  result = {
   *      code: int,
   *      content: string
   *  }
   * }
   */
  static getToken(callback) {
    JVerificationModule.getToken(callback);
  }

  /**
   * 手机号校验
   * @param {Object} params = {
   *      number: string,
   *      token: string
   * }
   * @param {Function} callback = (result) => {
   *  result = {
   *      code: int,
   *      content: string
   *  }
   * }
   */
  static verifyNumber(params, callback) {
    JVerificationModule.verifyNumber(params, callback);
  }

  /**
   * 请在SDK启动后调用本接口，调用本接口可打开日志级别为: Debug, 打印调试日志.
   * @param {boolean} enable
   */
  static setDebug(enable) {
    JVerificationModule.setDebug(enable);
  }

  /**
   * Android 权限获取:"android.permission.READ_PHONE_STATE"
   * @param {Function} callback = (result) => {
   *  result = {
   *      code: int,//0 获取成功，1 获取失败
   *      content: string
   *  }
   * }
   */
  static requestPermission(callback) {
    if (Platform.OS == "android") {
      JVerificationModule.requestPermission(callback);
    }
  }

  /**
   * @param {Function} callback = (result) => {
   *  result = {
   *      code: int,//0 获取成功，1 获取失败
   *      content: string
   *  }
   * }
   */
  static loginAuth(params, callback) {
    JVerificationModule.loginAuth(params, callback);
  }

  static preLogin(params, callback) {
    JVerificationModule.preLogin(params, callback);
  }
  static checkVerifyEnable(callback) {
    JVerificationModule.checkVerifyEnable(callback);
  }

  static clearPreloginCache() {
    JVerificationModule.clearPreloginCache();
  }

  static initClient(key, callback) {
    JVerificationModule.initClient(key, callback);
  }
}
