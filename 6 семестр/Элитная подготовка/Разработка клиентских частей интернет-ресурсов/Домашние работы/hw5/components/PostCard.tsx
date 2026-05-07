'use client'

import Link from 'next/link'
import { Post } from '@/types'
import { motion } from 'framer-motion'

export default function PostCard({ post }: { post: Post }) {
    return (
        <motion.div whileHover={{ scale: 1.02 }} className="rounded-xl border bg-white p-5 shadow-sm transition-shadow hover:shadow-md">
            <div className="mb-3 flex flex-wrap gap-2">
                {post.tags.map((t) => (
                    <span key={t} className="rounded-full bg-zinc-100 px-2 py-1 text-xs text-zinc-600">
                        {t}
                    </span>
                ))}
            </div>
            <h2 className="text-xl font-semibold leading-snug text-zinc-900">
                {post.title}
            </h2>
            <Link href={`/posts/${post.id}`} className="mt-5 inline-flex items-center text-sm font-medium text-blue-600 hover:text-blue-700">
                Читать пост →
            </Link>
        </motion.div>
    )
}
