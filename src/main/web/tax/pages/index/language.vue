<template>
	<view class="language-head">
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view slot="left" @click="toHome()">
				<uni-icons type="back"  size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default" >Language selection</view>
		</uni-nav-bar>
		<view class="">
			<uni-group title="" margin-top=""><!-- uni-group默认有一个外边框padding=15px -->
				<view class="selectLanguage-part">
					<text>Chinese</text>
					<view class="checkbox-view">
						<label>
							<checkbox :value="Chinese" /><text></text>
						</label>
					</view>
				</view>
				<view class="selectLanguage-part">
					<text>English</text>
					<view class="checkbox-view">
						<label>
							<checkbox :value="English" /><text></text>
						</label>
					</view>
				</view>
				<view class="selectLanguage-part">
					<text>Español</text>
					<view class="checkbox-view">
						<label>
							<checkbox :value="Español" /><text></text>
						</label>
					</view>
				</view>
				<view class="selectLanguage-part">
					<text>French</text>
					<view class="checkbox-view">
						<label>
							<checkbox :value="French" /><text></text>
						</label>
					</view>
				</view>
			</uni-group>
		</view>
		<button class="language-confirm-button">Confirm</button>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				
			}
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

<style>
</style>
