<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>smpl-web</title>
<style>
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
  }
  .header {
    background-color: #333;
    color: #fff;
    padding: 10px;
    display: flex;
    justify-content: space-between; /* 좌우 여백을 두기 위한 설정 */
    align-items: center; /* 세로 중앙 정렬을 위한 설정 */
  }
  .header-logo {
    display: flex;
    align-items: center;
  }
  .header-logo a {
    text-decoration: none;
    color: #fff;
    font-weight: bold;
    font-size: 1.2em;
    margin-right: 10px;
  }
  .sidebar {
    width: 250px;
    background-color: #f4f4f4;
    float: left;
    height: calc(100vh - 60px); /* 전체 높이에서 헤더 높이 빼기 */
  }
  .main-content {
    margin-left: 250px; /* 사이드바 너비와 동일하게 설정 */
    padding: 20px;
  }
  .footer {
    background-color: #333;
    color: #fff;
    padding: 10px;
    text-align: center;
    clear: both;
  }
  .sidebar ul {
    list-style: none;
    padding: 0;
  }
  .sidebar li {
    padding: 10px;
    border-bottom: 1px solid #ccc;
  }
  .user-info {
    float: right;
    padding: 20px;
    background-color: #f4f4f4;
    color: #333;
  }
</style>
<!-- <script src="/webjars/jquery/3.7.1/dist/jquery.min.js"></script> -->
<script type="text/javascript">
//$(document).ready(function() {
//  $('a[name=list_api]').click(function () {
//    //$('#txt_api').val('<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>' + $(this).text());
//    $('#txt_api').val('<%= request.getScheme()+"://"+request.getServerName()+":8080" %>' + $(this).text());
//  });
//
//  $('#btn_sel').click(function () {
//    $.ajax({
//      url: $('#txt_api').val(),
//      method: 'get'
//    })
//    .done(function(data) {
//      $('#div_rslt').text(JSON.stringify(data));
//    });
//  });
//});
</script>
</head>
<body>

<div class="header">
  <div class="header-logo">
    <a href="/">HOME</a>
  </div>
</div>

<!-- <a href="/login/oauth2/code/google" target="_blank">google 로그인</a> -->
<ul>
  <li><a href="/oauth2/authorization/google">google 로그인</a></li>
  <li><a href="/oauth2/authorization/kakao">kakao 로그인</a></li>
  <li><a href="/oauth2/authorization/naver">naver 로그인</a></li>
</ul>

</body>
</html>
