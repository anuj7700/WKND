const { post, get } = require("jquery");
var jQuery = require("jquery");

// Wrap bindings in anonymous namespace to prevent collisions
jQuery(function ($) {
    "use strict";

    function display(){
        var fragmentPath = $("input[name='portfolioslistpath']").val();
        console.log("fragment Path1 " + fragmentPath);
        if(fragmentPath !== undefined){
          var data1 = encodeURIComponent(fragmentPath);
          var URL = "/graphql/execute.json/wknd/getportfoliosbypath-query;apath=" + data1;
          console.log(URL);
          //var URI = "http://localhost:4502/graphql/execute.json/wknd/getportfoliosbypath-query;apath=" + data1;
          
          $.ajax({
              url: URL,
              dataType: "json",
              type: "GET",
              
              
              success: function(response){
                  
                  var str="<div>"
                  console.log(response);
                  var pfs = response.data.portfolioListByPath.item.includedPortfolios;
                  $(pfs).each(function(item){
                      var portfolio = pfs[item];
                      str = str + "<div><span>" + portfolio.description + "</span>";
                      str = str + "<img src='" + portfolio.portfolioImage._path + "'/>";
                      str = str + "</div>";
                          
                  })
                  str = str + "</div>";
                  $(".portfolioslist").append(str);
              }
            });
  
        }

        /*var data = JSON.stringify({"query":"{\n  portfolioListByPath(_path: \"/content/dam/wknd/global/en/portolios/portfolios-list-home\"){\n  \titem{\n      includedPortfolios {\n        _path\n        _variation\n        portfolioLogo{\n          ... on ImageRef {\n            _path\n        \t}\n        }\n        portfolioImage{\n          ... on ImageRef {\n            _path\n        \t}\n\t\t\t\t}\n        description\n      }\n    }\n  }    \n}","variables":null});
          
          var xhr = new XMLHttpRequest();
          //xhr.withCredentials = true;
          
          xhr.addEventListener("readystatechange", function () {
            if (this.readyState === 4) {
              console.log(this.responseText);
              console.log();
            }
          });
          
          var queryPath = "wknd/getportfoliosbypath-query"
          var fragmentPath = $('input[name="portfolioslistpath"]').val();
          xhr.open("POST", "http://localhost:4502/content/_cq_graphql/wknd/endpoint.json");
          xhr.setRequestHeader("authorization", "Basic YWRtaW46YWRtaW4=");
          xhr.setRequestHeader("content-type", "application/json");
         xhr.send(data);*/
    }

    display();
});
