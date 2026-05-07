'use client'

import { useState } from 'react'
import Image from 'next/image'
import { motion, AnimatePresence } from 'framer-motion'
import { Comment } from '@/types'

export default function CommentsSection({ comments, }:
{ comments: Comment[] })
{
    const [expanded, setExpanded] = useState(false)
    const sorted = [...comments].sort((a, b) => b.id - a.id)
    const visible = expanded ? sorted : sorted.slice(0, 3)

    return (
        <div className="mt-10">
            <div className="flex items-center justify-between">
                <h2 className="text-2xl font-bold">
                    Комментарии
                </h2>
                {sorted.length > 3 && (
                    <button onClick={() => setExpanded((v) => !v)} className="text-sm text-blue-600 hover:underline">
                        {expanded ? 'Свернуть ▲' : 'Показать все ▼'}
                    </button>
                )}
            </div>
            {sorted.length === 0 ? (
                <div className="mt-4 rounded-lg border border-dashed p-6 text-center text-zinc-500">
                    Комментариев пока нет
                </div>
            ) : (
                <motion.div layout className="mt-4 flex flex-col gap-3">
                    <AnimatePresence>
                        {visible.map((c) => (
                            <motion.div
                                key={c.id} layout
                                initial={{ opacity: 0, y: 10, }}
                                animate={{ opacity: 1, y: 0, }}
                                exit={{ opacity: 0, y: -10, }}
                                transition={{ duration: 0.2, }}
                                className="flex gap-3 rounded-xl border bg-white p-4 shadow-sm"
                            >
                                <div className="h-10 w-10 overflow-hidden rounded-full bg-zinc-200">
                                    {c.image ? (
                                        <Image src={c.image} alt="avatar" width={40} height={40} className="h-full w-full object-cover"/>
                                    ) : (
                                        <div className="flex h-full items-center justify-center font-semibold text-zinc-600">
                                            {c.author[0]}
                                        </div>
                                    )}
                                </div>
                                <div className="flex-1">
                                    <div className="flex items-baseline gap-2">
                                        <div className="text-sm font-semibold leading-none">
                                            {c.author}
                                        </div>
                                        <div className="text-xs leading-none text-zinc-400">
                                            {c.createdAt}
                                        </div>
                                    </div>
                                    <div
                                        className="mt-1 text-sm leading-6 text-zinc-700"
                                        style={{ whiteSpace: 'pre-wrap', wordBreak: 'break-word', }}
                                    >
                                        {c.text}
                                    </div>
                                </div>
                            </motion.div>
                        ))}
                    </AnimatePresence>
                </motion.div>
            )}
        </div>
    )
}
