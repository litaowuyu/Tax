<template>
	<view>
		<iHeader></iHeader>
		<view class="person">
			<view class="usercenter">
				<view>
					<image src="../../static/images/avatar.png"></image>
				</view>
				<view class="personInfo">
					<view class="username">
						<!-- 读取对象中获取数据  {{对象名.键}} 例如{{userinfo.username}}-->
						{{$t('index.me.username')}} :
						{{userInfo.phone}}
					</view>
					<view class="ID">
						{{$t('index.me.ID')}} :
						{{userInfo.id}}
						
					</view>
					<view class="inviteCode">
						{{$t('index.me.inviteCode')}} :
						{{userInfo.uni_id}}
						
					</view>
				</view>
				<view class="vip">
					<view class="vipText">
						VIP{{userInfo.vip_level}}
					</view>
				</view>
			</view>
			<view class="balanceInfo">
				<view class="balance">
					<view class="name">
						{{$t('index.me.Balance')}}
						<view class="number">
						{{userInfo.balance}}
						</view>
					</view>

					<view class="refresh">
						<uni-icons type="refreshempty" size="30" color="#30abff" @click="getUserInfo()"></uni-icons>
					</view>
				</view>
				<button class="rechangeBtn" @click="recharge()">{{$t('index.me.recharge')}}</button>
				<button class="withdrawBtn" @click="withdrawBtn()" >{{$t('index.me.withdraw')}}</button>
			</view>
		</view>
		<view class="listMethod"@click="withdraw()" >
			<uni-icons type="wallet" size="30" color="#30abff"></uni-icons>
			<view class="textlocation">{{$t('index.me.wallet')}}</view>
			<uni-icons type="forward" size="30" color="#30abff"></uni-icons>
		</view>
		<view class="listMethod">
			<uni-icons type="list" size="30" color="#30abff"></uni-icons>
			<view class="textlocation" @click="toTransaction()">{{$t('index.me.transaction')}}</view>
			<uni-icons type="forward" size="30" color="#30abff"></uni-icons>
		</view>
		<view class="listMethod" @click="tolink()">
			<uni-icons type="staff" size="30" color="#30abff"></uni-icons>
			<view class="textlocation">{{$t('index.me.invationCode')}}</view>
			<uni-icons type="forward" size="30" color="#30abff"></uni-icons>
		</view>
		<view class="listMethod" @click="resetPassword()">
			<uni-icons type="locked" size="30" color="#30abff"></uni-icons>
			<view class="textlocation">{{$t('index.me.resetPassword')}}</view>
			<uni-icons type="forward" size="30" color="#30abff"></uni-icons>
		</view>
		<button @click="logout()" class="exitBtn">{{$t('index.me.exit')}}</button>
	</view>

</template>

<script>
	import iHeader from "component/iHeader.vue";
	import util from '../../static/js/util.js';
	export default {

		data() {
			return {
				//声明一个对象用来接收后端传来的参数
				userInfo: {

				}
			}
		},
		onLoad() {

		},
		onShow() {
			let accessToken = uni.getStorageSync('accessToken');
			if (accessToken == '') {
				uni.navigateTo({
					url: '/pages/me/login'
				});
			} else {
				this.getUserInfo();
			}
		},
		methods: {
			getUserInfo(showLoading = false) {
				let that=this;
				this.iTools.request('Auth/me/userInfo', {}, 'GET', function(data) {
					that.$data.userInfo=data.data;
					that.$data.userInfo.balance=util.regFenToYuan(that.$data.userInfo.balance);
				}, showLoading);
			},
			logout() {
				uni.removeStorageSync('hasPhone');
				uni.removeStorageSync('hasPassword');
				uni.removeStorageSync('accessToken')
				uni.removeStorageSync('areaCode');
				uni.switchTab({
					url: '/pages/index/index'
				});
			},
			recharge(){
				 uni.navigateTo({
					url:'/pages/me/recharge'
				})
			},
			withdraw(){
				uni.navigateTo({
					url:'/pages/me/withdrawalMethods'
				});
			},
			resetPassword(){
				uni.navigateTo({
					url:'/pages/me/resetpwd'
				})
			},
			tolink(){
				uni.navigateTo({
					url:'/pages/me/link'
				})
			},
			withdrawBtn(){
				uni.navigateTo({
					url:'/pages/me/withdrawBtn'
				})
			},
			toTransaction(){
				uni.navigateTo({
					url:'/pages/me/transaction'
				})
			}
		},

		components: {
			iHeader
		}
	}
</script>

<style>
	.uni-tag {
		font-weight: bold;
		color: #e7e700;
		line-height: 50px;
		font-size: 15px;
	}

</style>
