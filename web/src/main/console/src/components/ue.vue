<template>
    <div>
        <!-- Ueditor编辑器需要一个keep-alive标签 -->
        <keep-alive>
            <neditor ref="neditor"
                    id="neditor"
                    v-bind:r_content="setForm.rich_text">
            </neditor>
        </keep-alive>
    </div>
</template>
<script>
    import neditor from '@/components/editor'
    export default {
        data(){
            return {
                setForm: {
                    rich_text: ""
                }
            }
        }, components: {
            neditor: neditor
        }
    }
</script>
