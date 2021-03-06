<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0,user-scalable=no,minimal-ui">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../img/asset-favico.ico">
    <title>预约</title>
    <link rel="stylesheet" href="../css/page-health-order.css"/>
</head>
<body data-spy="scroll" data-target="#myNavbar" data-offset="150">
<div class="app" id="app">
    <!-- 页面头部 -->
    <div class="top-header">
        <span class="f-left"><i class="icon-back" onclick="history.go(-1)"></i></span>
        <span class="center">传智健康</span>
        <span class="f-right"><i class="icon-more"></i></span>
    </div>
    <!-- 页面内容 -->
    <div class="contentBox">
        <div class="list-column1">
            <ul class="list">
                <#list setMealList as setMeal>
                    <li class="list-item">
                        <a class="link-page" href="setmeal_detail_${setMeal.id}.html">
                            <#if setMeal.img??>
                                <img class="img-object f-left"
                                     src="http://qkw9ugnl7.hn-bkt.clouddn.com/${setMeal.img}"
                                     alt="">
                            <#else>
                                <img class="img-object f-left"
                                     src="../img/default.png"
                                     alt="">
                            </#if>
                            <div class="item-body">
                                <h4 class="ellipsis item-title">${setMeal.name}</h4>

                                <p class="ellipsis-more item-desc">
                                    ${setMeal.remark!"暂未做说明，请反馈给客服人员及时添加。"}
                                </p>
                                <p class="item-keywords">
                                    <span>
                                        <#if setMeal.sex??>
                                            <#if setMeal.sex == '0'>
                                                性别不限
                                            <#else>
                                                <#if setMeal.sex == '1'>
                                                    男
                                                <#else>
                                                    女
                                                </#if>
                                            </#if>
                                        <#else>
                                            未设置性别
                                        </#if>
                                       </span>
                                    <span>${setMeal.age!"暂无年龄信息"}</span>
                                </p>
                            </div>
                        </a>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</div>
<!-- 页面 css js -->
<script src="../plugins/vue/vue.js"></script>
<script src="../plugins/vue/axios-0.18.0.js"></script>

</body>