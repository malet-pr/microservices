<template>
    <div class="flex flex-column align-items-center justify-content-center min-h-screen">
        <h2>Select Quantity and Topic</h2>
        <Toast position="top-center"  @close="reset"/>
        <Form v-slot="$form" :initialValues :resolver :validateOnValueUpdate="true" @submit="onFormSubmit" @reset="reset" class="w-6">
            <FloatLabel variant="on">
                <InputText type="text" name="topic" id="topic" class="bg-primary-100 border-round min-w-full h-3rem p-3 my-2"/>
                <label for="topic">Topic</label>
                <Message v-if="$form.topic?.invalid" severity="error" size="small" variant="simple">{{ $form.topic.error?.message }}</Message>
            </FloatLabel>
            <FloatLabel variant="on">
                <InputText type="number" name="quantity" id="quantity" :min="0" :max="10" showButtons class="bg-primary-100 border-round min-w-full h-3rem p-3 my-2"/>
                <label for="quantity">Quantity</label>
                <Message v-if="$form.quantity?.invalid" severity="error" size="small" variant="simple">{{ $form.quantity.error?.message }}</Message>
            </FloatLabel>   
            <div class="flex flex-row">        
                <Button type="reset" outlined severity="secondary" label="Clean" class="my-4 mx-auto"/>   
                <Button type="submit" severity="secondary" label="Submit" class="my-4 mx-auto"/>
            </div>
        </Form>
    </div>
</template>

<script setup>
    import '/node_modules/primeflex/primeflex.css';

    const toast = useToast();
    //const {reset} = useForm();
    const initialValues = reactive({topic:'',quantity:''});
    const to = ref('topic');
    const qu = ref(0);

    const resolver = ({ values }) => {
        const errors = {};
        if (!values.topic) {
            errors.topic = [{ message: 'Topic is required.' }];
        } else {
            to.value = values.topic
        }
        if (!values.quantity || Number(values.quantity) < 1) {
            errors.quantity = [{ message: 'Quantity is required. '}];
        } else {
            qu.value = values.quantity
        }
        return {
            errors
        };
    };

    const onFormSubmit = ({ valid }) => {
        if (valid) {
            toast.add({
                severity: 'success',
                summary: 'Form is submitted.',
                detail: `Sent ${qu.value} orders to be generated and posted to ${to.value} topic.`
            });
        }
    };

    // it should be handleled better than reloading the page. 
    // There is supposedly a useForm that refreshes the fields but it's not working.
    const reset = () => {
        console.log('inside clean funcion')
        location.reload(); 
    }

</script>

<style scoped>
    input.error {
        border-color: var(--p-inputtext-invalid-border-color);
    }
</style>