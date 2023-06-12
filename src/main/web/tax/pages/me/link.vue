<template>
	<view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back" size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default">Invation Code & Link</view>
		</uni-nav-bar>
		<view class="invite">
			<view class="inviteBox">
				<image class="inviteimg" mode="widthFix" src="/static/images/invitelink/6.png"></image>
			</view>
			

			<view class="inviteCode1">
				<view class="words">
					Make money from inviting others!
				</view>
				<view class="code">
					{{invationCode}}
				</view>
			</view>
		</view>
		<view class="inviteLink">
			<view class="words">
				Copy and share with friends
			</view>
			<view class="link">
				<view class="title">
					<input type="text" disabled="true" placeholder="https://ask.dcloud.net.cn/explore/" />
				</view>
				<view>
					<button type="primary" size="mini">Copy</button>
				</view>
			</view>
		</view>
		<view class="items2">
			<view class="item2">
				<image src="/static/images/invitelink/1.png" mode="widthFix"></image>
			</view>
			<view class="item2">
				<image src="/static/images/invitelink/2.png" mode="widthFix"></image>
			</view>
			<view class="item2">
				<image src="/static/images/invitelink/3.png" mode="widthFix"></image>
			</view>
			<view class="item2">
				<image src="/static/images/invitelink/4.png" mode="widthFix"></image>
			</view>
			<view class="item2">
				<image src="/static/images/invitelink/5.png" mode="widthFix"></image>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				invationCode:''
			}
		},
		onShow(){
			this.loadUserInvationCode()
		},
		methods:{
			toHome(){
				uni.switchTab({
					url:'/pages/me/index'
				})
			},
			loadUserInvationCode(){
				let that = this;
				this.iTools.request("Auth/me/userInfo", {}, "GET", function(result){
					console.log(result.data);
					that.$data.invationCode = result.data.uni_id;
				},true)
			}
		}
	}
</script>

<style>
</style>
