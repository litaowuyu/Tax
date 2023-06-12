<template>
	<view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="back()">
				<uni-icons type="back" size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default">{{$t('me.WithdrawalMethods.name')}}</view>
		</uni-nav-bar>
		<view class="account" v-for="(item,index) in accountList">
			<view style="margin: auto 0;">
				<uni-icons type="wallet" size="40"></uni-icons>
			</view>
			<view class="accountName" >
				<view style="padding-top: 5px; ">
					{{item.account}}
				</view>
				<view class="account_name">
					{{item.address}}
				</view>
			</view>
			<view style="margin: auto 0;" @click="deleteAccount(item.id)">
				<uni-icons type="trash" size="40"></uni-icons>
			</view>
		</view>
		<view>
			<button class="addaccount" @click="addacount()">{{$t('me.WithdrawalMethods.addaccount')}} </button>
		</view>
		<view class="tips">
			<text>
				{{$t('me.WithdrawalMethods.tips')}}
			</text>
			<br>
			<text class="tips_1">
				{{$t('me.WithdrawalMethods.tips_1')}}
			</text>
			<br>
			<text class="tips_2">
				{{$t('me.WithdrawalMethods.tips_2')}}
			</text>
			<br>
			<text class="tips_3">
				{{$t('me.WithdrawalMethods.tips_3')}}
			</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				accountList: {}
			}
		},onShow() {
			this.getUserAccount();
		},
		methods: {
			addacount() {
				uni.navigateTo({
					url: '/pages/me/withdrawalMethods-addacount'
				})
			},
			back() {
				uni.switchTab({
					url: '/pages/me/index'
				})
			},
			getUserAccount() {
				let that =this;
				this.iTools.request('Auth/getUserAccount', {}, 'GET', function(data) {
					// console.log("9999")
					that.$data.accountList = data.data;
					// console.log(1111111111111);
					console.log(that.$data.accountList);
					// console.log(2222222);
					
				}, true);
			},
			deleteAccount(id){
				//这里的id是tax_account中的id
				let that=this;
				console.log("当前id"+id);
				uni.showModal({
					title: 'Tips',
					content: 'Do you confirm that you want to delete the wallet?',
					cancelText: "NO",
					confirmText: "Yes",
					success: function (res) {
						if (res.confirm) {
							that.iTools.request('Auth/deleteAccount',{
								id:id
							},'POST',function(data){
								if(data.code==0){
									uni.showToast({
										title:"success",
										mask:true,
										duration:2500
									});
									//刷新一下界面
									uni.navigateTo({
										url:'/pages/me/withdrawalMethods'
									})
								}else{
									uni.showToast({
										title:"error",
										mask:true,
										duration:2500
									})
								}
							},true);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
		
			}
		},
	}
</script>

<style>
	.account {
		background-color: white;
		margin: 10px;
		border-radius: 7px;
		display: flex;
		justify-content: space-between;
		border: 1px solid #ccc;

	}

	.account>.accountName {
		width: 100%;
		padding: 0px 20px;
	}

	.account>.accountName>.account_name {
		font-size: 14px;
		color: #ccc;
	}

	.account>input {
		border: 1px solid #ccc;
		font-size: 20px;
		margin: 0px auto;
		height: 45px;
		line-height: 45px;
		width: 93%;
		border-radius: 5px;
		text-indent: 10px;

	}

	.addaccount {
		color: #FFFFFF;
		background-color: #007AFF;
		border: 0px solid #ccc;
		font-size: 20px;
		margin: 0px auto;
		margin-top: 20px;
		height: 45px;
		line-height: 30px;
		width: 96%;
		border-radius: 5px;
		padding: 5px;
	}

	.tips {
		width: 95%;
		margin: 0px auto;
		margin-top: 40px;
		text-align: left;
		line-height: 30px;
	}

	.tips>text {
		display: block;
		margin-top: -20px;
		font-size: 12px;
	}
</style>
