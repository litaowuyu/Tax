<template>
	<view class="">
		<uni-nav-bar  backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back"  size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default" >Events</view>
			<view class="" slot="right" @click="choiceLanguage()">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
		<view class="events-body">
		<h3 id="p1">Sky Taxrefund</h3>
		<p id="p2">
		  Sky Taxrefund promotion is currently open for a limited time! Please
		  follow the event posters below for more details.
		</p>
		<img src="/static/images/events-pic/events-pic1.png"/>
		<img src="/static/images/events-pic/events-pic2.png"/>
		</view>
	</view>
	
</template>

<script>
export default {
	data(){
		return{}
	},
	methods:{
		toHome(){
			uni.switchTab({
				url:"/pages/index/index"
			});
		}
	}
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3{
	text-align: center;
}
p {
  margin: 20px 0 10px;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
img{
	width: 100%;
}
.events-body{
	padding: 10px;
}
</style>
