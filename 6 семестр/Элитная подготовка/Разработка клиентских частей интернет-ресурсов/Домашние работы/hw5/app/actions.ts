'use server'

import { auth } from '@/auth'
import { comments } from '@/data/comments'

function formatDate(date: Date) {
    return new Intl.DateTimeFormat('ru-RU', {
        day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit',
    }).format(date)
}

export async function addComment(formData: FormData) {
    const session = await auth()
    if (!session?.user) { throw new Error('Unauthorized') }
    const postId = Number(formData.get('postId'))
    const text = String(formData.get('text'))

    comments.push({ id: Date.now(), postId, author: session.user.name || 'User', text, image: session.user.image || undefined, createdAt: formatDate(new Date()), })
}
