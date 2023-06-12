<template>
	<view >
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff" >
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back"  size="25" color="#ffffff"></uni-icons>
			</view>
			<view slot="default" class="register-title" >{{$t('me.Teamrules.name')}}</view>
		</uni-nav-bar>

		<view class="tips">
			<view class="tips_1">
				{{$t('me.Teamrules.1')}}
			</view>
			<br>
			<view class="tips_2">
				{{$t('me.Teamrules.2')}}
			</view>
			<br>
			<view class="tips_3">
				{{$t('me.Teamrules.3')}}
			</view>
			<br>
			<view class="tips_4">
				{{$t('me.Teamrules.4')}}
			</view>
			<br>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			toHome(){
				uni.switchTab({
					url:"/pages/myTeam/index"
				});
			}
		}
	}
</script>

<style>
	.mainBody{
		
	}
	.account{
		background-color: #FFFFFF;
		width:100%;
		height:80px;
		margin-top: 10px;
		padding-top: 20px;
	}
	.account>input{
		border: 1px solid #ccc;
		font-size: 20px;
        margin: 0px auto;
		height: 45px;
		line-height: 45px;
		width: 93%;
		border-radius: 5px;
		text-indent: 10px;
		
	}
	.addaccount{
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
	.tips{
		width: 95%;
		margin: 0px auto;
		/* margin-top: 40px; */
		text-align:left;
		line-height: 30px;
		margin: 10px;
		background-color: white;
		border-radius: 7px;
	}
	.tips>view{
		display: inline-block;
		padding: 10px;
		/* margin-top: -20px; */
		font-size: 16px;
	}
</style>

