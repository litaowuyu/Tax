<template name="iHeader">
	<view>
		<view class="status_bar"></view>

		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view solt="default" class="topBarText">
				{{$t('head.appName')}}
			</view>
			<view slot="right" @click="selectLanguage()">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {}
		},
		name: "iHeader",
		methods: {
			selectLanguage() {
				Console.log(111111)
				uni.navigateTo({
					url: "/pages/index/language"
				})
			},
		}
	}
</script>

<style>
</style>
