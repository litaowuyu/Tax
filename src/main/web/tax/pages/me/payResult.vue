<template>
	<view>
		<view class="status_bar">
		</view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view slot="left" @click="goHome">
				<uni-icons type="arrow-left" size="30" color="white"></uni-icons>
			</view>
			<view slot="default" class="topBarText">{{$t('index.me.recharge')}}</view>
			<view slot="right">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
	</view>
</template>

<script>
	export default {
		onShow() {

		},
		data() {
			return {

			}
		},
		methods: {
			goHome() {
				uni.switchTab({
					url: '/pages/me/index'
				});
			},
		}
	}
	
</script>

<style>
</style>
