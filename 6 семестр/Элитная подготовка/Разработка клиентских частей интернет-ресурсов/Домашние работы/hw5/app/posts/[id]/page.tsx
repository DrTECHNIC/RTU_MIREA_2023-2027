import { posts } from '@/data/posts'
import { comments } from '@/data/comments'
import { auth } from '@/auth'
import CommentForm from '@/components/CommentForm'
import CommentsSection from '@/components/CommentsSection'
import { notFound } from 'next/navigation'

interface Props {
    params: Promise<{ id: string }>
}

export default async function PostPage({ params }: Props) {
    const { id } = await params
    const postId = Number(id)
    const post = posts.find((p) => p.id === postId)
    if (!post) notFound()
    const postComments = comments.filter((c) => c.postId === postId)
    const session = await auth()
    const readingTime = Math.max(
        1,
        Math.ceil(post.content.split(' ').length / 120)
    )

    function pluralize( count: number, one: string, few: string, many: string ) {
        const mod10 = count % 10
        const mod100 = count % 100
        if (mod10 === 1 && mod100 !== 11) { return one }
        if (mod10 >= 2 && mod10 <= 4 && !(mod100 >= 12 && mod100 <= 14)) { return few }
        return many
    }

    return (
        <article className="mx-auto max-w-3xl">
            <div className="mb-4 flex flex-wrap gap-2">
                {post.tags.map((tag) => (
                    <span key={tag} className="rounded-full bg-zinc-200 px-3 py-1 text-xs text-zinc-700">
                        {tag}
                    </span>
                ))}
            </div>
            <h1 className="text-4xl font-bold tracking-tight text-zinc-900">
                {post.title}
            </h1>
            <div className="mt-3 flex items-center gap-3 text-sm text-zinc-500">
                <span>{readingTime} минут{pluralize(readingTime, 'а', 'ы', '')} чтения</span>
                <span>•</span>
                <span>{postComments.length} комментари{pluralize(postComments.length, 'й', 'я', 'ев')}</span>
            </div>
            <div className="mt-8 rounded-xl border bg-white p-6 shadow-sm">
                <div className="mt-6 text-[15px] leading-7 text-zinc-700">
                    {post.content.split('\n\n').map((paragraph, index) => (
                        <div key={index} className="mb-6">
                            {paragraph.split('\n').map((line, lineIndex) => (
                                <div key={lineIndex} className="whitespace-pre-wrap break-words">
                                    {<div key={lineIndex} className="whitespace-pre-wrap break-words">
                                        {line}
                                    </div>}
                                </div>
                            ))}
                        </div>
                    ))}
                </div>
            </div>
            <section className="mt-10 rounded-2xl border bg-zinc-50 p-6 shadow-sm">
                <div className="mb-6 border-b pb-4">
                    <h2 className="text-2xl font-bold text-zinc-900">
                        Обсуждение
                    </h2>
                    <p className="mt-1 text-sm text-zinc-500">
                        Делитесь мнением и обсуждайте тему поста
                    </p>
                </div>
                <div>
                    {session?.user ? (
                        <CommentForm postId={postId} />
                    ) : (
                        <div className="rounded-lg border border-dashed bg-white p-4 text-sm text-zinc-500">
                            Войдите, чтобы оставить комментарий
                        </div>
                    )}
                </div>
                <CommentsSection comments={postComments} />
            </section>
        </article>
    )
}
