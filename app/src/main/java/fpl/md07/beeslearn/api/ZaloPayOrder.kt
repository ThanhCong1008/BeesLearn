package fpl.md07.beeslearn.api

import java.util.Date

data class ZaloPayOrder(
    var appId: String = AppInfo.APP_ID.toString(),
    var appUser: String = "Android_Demo",
    var appTime: String = Date().time.toString(),
    var amount: String,
    var appTransId: String = Helpers.getAppTransId(),
    var embedData: String = "{\"promotioninfo\":\"\",\"merchantinfo\":\"embeddata123\"}",
    var items: String = "[{\"itemid\":\"knb\",\"itemname\":\"kim nguyen bao\",\"itemprice\":198400,\"itemquantity\":1}]",
    var bankCode: String = "zalopayapp",
    var description: String = "Merchant pay for order #${Helpers.getAppTransId()}",
    var mac: String = Helpers.getMac(
        AppInfo.MAC_KEY,
        data = "$appId|$appTransId|$appUser|$amount|$appTime|$embedData|$items"
    )
)
