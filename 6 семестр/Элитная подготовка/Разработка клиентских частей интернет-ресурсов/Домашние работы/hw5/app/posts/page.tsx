'use client'

import { useState } from 'react'
import { motion } from 'framer-motion'
import { posts } from '@/data/posts'
import PostCard from '@/components/PostCard'

export default function PostsPage() {
    const [query, setQuery] = useState('')
    const filtered = posts.filter((p) => {
        const searchable = `${p.title} ${p.content} ${p.tags.join(' ')}`.toLowerCase()

        return searchable.includes(query.toLowerCase())
    })

    return (
        <div className="space-y-6">
            <h1 className="text-4xl font-bold">
                Посты
            </h1>
            <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Поиск..." className="w-full rounded border p-3"/>
            {filtered.length === 0 ? (
                <div className="rounded border border-dashed p-10 text-center text-zinc-500">
                    Ничего не найдено
                </div>
            ) : (
                <div className="grid gap-4 md:grid-cols-2">
                    {filtered.map((post) => (
                        <motion.div key={post.id} initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }}>
                            <PostCard post={post}/>
                        </motion.div>
                    ))}
                </div>
            )}
        </div>
    )
}
