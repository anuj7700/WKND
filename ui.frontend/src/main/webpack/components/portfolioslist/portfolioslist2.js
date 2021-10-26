import Vue from 'vue';
import Axios from "Axios";

var app = new Vue({
    el: '#portfoliosListVueApp',
    data:{
        message: "Hello World",
        portfoliosList: []
    },

    mounted(){
        let fragmentPath = document.querySelector("input[name='portfolioslistpath']").getAttribute("value");
        this.message = fragmentPath;
        if(fragmentPath !== undefined){
          var data1 = encodeURIComponent(fragmentPath);
          var URL = "/graphql/execute.json/wknd/getportfoliosbypath-query;apath=" + data1;
          let config = {
              headers: {
                authorization: "Basic YWRtaW46YWRtaW4="
              }
          }
          Axios.get(URL, config)
            .then(response =>{
                this.portfoliosList = response.data.data.portfolioListByPath.item.includedPortfolios;
                console.log("Portfolio List" + this.portfoliosList);
            });
        }
    }
})