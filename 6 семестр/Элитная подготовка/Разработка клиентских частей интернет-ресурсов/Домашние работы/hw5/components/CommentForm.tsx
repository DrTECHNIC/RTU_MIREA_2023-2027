'use client'

import { useRef } from 'react'
import { addComment } from '@/app/actions'
import { useRouter } from 'next/navigation'

export default function CommentForm({ postId, }:
{ postId: number })
{
    const formRef = useRef<HTMLFormElement>(null)
    const router = useRouter()

    async function action(formData: FormData) {
        await addComment(formData)
        formRef.current?.reset()
        router.refresh()
    }

    function handleKeyDown(
        e: React.KeyboardEvent<HTMLTextAreaElement>
    ) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault()
            const form = e.currentTarget.form
            if (form) {form.requestSubmit()}
        }
    }

    return (
        <form ref={formRef} action={action} className="rounded-xl border bg-white p-4 shadow-sm">
            <input type="hidden" name="postId" value={postId}/>
            <textarea name="text" required rows={4} onKeyDown={handleKeyDown} placeholder="Напишите комментарий..." className="w-full resize-none rounded-lg border p-3 outline-none focus:border-zinc-400"/>
            <div className="mt-2 flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
                <div className="flex flex-col text-xs text-zinc-500">
                    <div>Enter — отправить</div>
                    <div>Shift + Enter — новая строка</div>
                </div>
                <button className="rounded-lg bg-zinc-900 px-4 py-2 text-white hover:bg-zinc-800">
                    Отправить
                </button>
            </div>
        </form>
    )
}
