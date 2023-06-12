<template>
	<view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff" left-icon="left" @clickLeft="back()">
			<view slot="default" class="register-title">{{$t('me.addacount.name')}}</view>
		</uni-nav-bar>
		<view class="add">

			<uni-data-picker class="select" v-model="default_account" :ellipsis="false" :clear-icon="false" :localdata="accountList">
			</uni-data-picker>
			<textarea v-model="address" class="walletaddress"
				:placeholder="$t('me.addacount.walletaddress')"></textarea>
			<textarea v-model="address_again" class="again" :placeholder="$t('me.addacount.again')"></textarea>

		</view>
		<view>
			<button class="addaccount" @click="addAccount()">{{$t('me.addacount.confirm')}} </button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				accountList: [{
					text: 'TRC20',
					value: 'TRC20',
					selected: true
				}, {
					text: '1',
					value: '1'

				}, {
					text: '2',
					value: '2'
				}],
				address:'',
				address_again:'',
				default_account:'TRC20'
			}
		},
		methods: {
			addAccount() {
				let address=this.$data.address;
				let address_again=this.$data.address_again;
				let default_account=this.$data.default_account;
				console.log(default_account);
				// 判断非空
				if (address == '' || address_again == '') {
					uni.showToast({
						title: "Accounts cannot be empty",
						mask: true,
						duration: 2500
					});
				} else {
					//判断两次相等
					if (address == address_again) {
						this.iTools.request('Auth/addAccount', {
							default_account: default_account,
							address: address
						}, 'POST', function(data) {
                            //判断添加成功
							if (data.code == 0) {
								uni.showToast({
									title: 'success',
									mask: true,
									duration: 2000,

								});
								uni.navigateTo({
									url: '/pages/me/withdrawalMethods'
								})
							}
						}, true);
					}else{
						uni.showToast({
							title: 'Enter the account is not consistent',
							mask: true,
							duration: 2500
						});
					};
				}
			},
			back() {
				uni.navigateTo({
					url:'/pages/me/withdrawalMethods'
				})
			}

		}
	}
</script>

<style>
	.add {
		background-color: #FFFFFF;
		width: 100%;
		height: 280px;
		margin-top: 10px;
		padding-top: 20px;

	}

	.add>input {
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

	.walletaddress {
		margin: 0px auto;
		margin-top: 10px;
		border: 1px solid #ccc;
		width: 96%;
		height: 90px;
		text-indent: 10px;
		border-radius: 5px;
		padding-top: 5px;

	}

	.again {
		margin: 0px auto;
		margin-top: 10px;
		border: 1px solid #ccc;
		width: 96%;
		height: 90px;
		text-indent: 10px;
		border-radius: 5px;
		padding-top: 5px;

	}

	.select {
		width: 96%;
		margin: 0px auto;
		border: 1px solid #ccc;
		border-radius: 5px;
	}
</style>
