<template>
	<view>
		<view class="status_bar">
		</view>
		<uni-nav-bar @clickLeft="clickLeft" backgroundColor="#007fff" color="#ffffff" leftIcon="arrow-left">
			<view slot="default" class="topBarText">{{$t('global.appName')}}</view>
		</uni-nav-bar>
		<uni-card>
			<uni-list>
				<uni-list-item :title='item.text' v-for="(item, index) in locales" clickable :key="index"
					@click="onLocaleChange(item)" showSwitch :switchChecked="item.code==currentLang?true:false">
				</uni-list-item>
			</uni-list>
		</uni-card>
		<view>
			<button type="primary" @click="submitLang">{{$t('global.confirm')}}</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				currentLang: uni.getLocale()
			}
		},
		computed: {
			locales() {
				return [{
					text: this.$t('local.en'),
					code: 'en'
				}, {
					text: this.$t('local.lt'),
					code: 'lt'
				}, {
					text: this.$t('local.ru'),
					code: 'ru'
				}]
			}
		},
		onLoad() {
			let systemInfo = uni.getSystemInfoSync();
			this.isAndroid = systemInfo.platform.toLowerCase() === 'android';
		},
		methods: {
			clickLeft() {
				/*返回上一级页面*/
				uni.navigateBack();
			},
			submitLang(e) {
				let that = this;
				let currentLangCode = this.$data.currentLang;
				if (that.isAndroid) {
					uni.showModal({
						content: that.$t('language-change-confirm'),
						success: (res) => {
							if (res.confirm) {
								uni.setLocale(currentLangCode);
								that.$i18n.locale = currentLangCode;
							}
						}

					});
				} else {
					this.$i18n.locale = currentLangCode;
					uni.setLocale(currentLangCode);
					// uni.navigateBack();
				}
				uni.showToast({
					title: 'success',
					success() {
						uni.navigateBack();
					}
				});
			},
			onLocaleChange(e) {
				this.$data.currentLang = e.code;
			}

		}

	}
</script>

<style>
</style>
