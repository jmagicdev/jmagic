package org.rnd.jmagic.engine;

/** Represents a "pool" of mana. */
public class ManaPool implements java.util.SortedSet<ManaSymbol>, java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private java.util.SortedSet<ManaSymbol> _delegate;

	private java.util.List<ManaSymbol> originalOrder;

	/** Creates an empty mana pool. */
	public ManaPool()
	{
		this._delegate = new java.util.TreeSet<ManaSymbol>();
		this.originalOrder = new java.util.LinkedList<ManaSymbol>();
	}

	/**
	 * Creates a mana pool containing the specified symbols.
	 * 
	 * @param c The symbols the mana pool is to contain.
	 */
	public ManaPool(java.util.Collection<? extends ManaSymbol> c)
	{
		this._delegate = new java.util.TreeSet<ManaSymbol>();
		this._delegate.addAll(c);

		this.originalOrder = new java.util.LinkedList<ManaSymbol>();
		this.originalOrder.addAll(c);
	}

	/**
	 * Creates a mana pool containing the same mana symbols as the given mana
	 * pool. The "original order" of <code>toCopy</code> will be maintanied in
	 * this pool.
	 * 
	 * @param toCopy The symbols the mana pool is to contain.
	 */
	public ManaPool(ManaPool toCopy)
	{
		this._delegate = new java.util.TreeSet<ManaSymbol>();
		this._delegate.addAll(toCopy);

		this.originalOrder = new java.util.LinkedList<ManaSymbol>();
		this.originalOrder.addAll(toCopy.originalOrder);
	}

	/**
	 * Creates a mana pool containing all symbols in a collection _except_ for a
	 * specific symbol.
	 * 
	 * @param c The symbols the mana pool is to contain.
	 * @param ignore The symbol in c not to add to this collection.
	 */
	public ManaPool(java.util.Collection<? extends ManaSymbol> c, ManaSymbol ignore)
	{
		this._delegate = new java.util.TreeSet<ManaSymbol>();
		this.originalOrder = new java.util.LinkedList<ManaSymbol>();
		for(ManaSymbol s: c)
			if(!s.equals(ignore))
			{
				this._delegate.add(s);
				this.originalOrder.add(s);
			}
	}

	/**
	 * Creates a mana pool from a string of characters.
	 * 
	 * @param poolString The string representing the mana pool to create.
	 * 
	 * '0' through '9' are translated into a symbol which is that many
	 * colorless. 'W' or 'w' is translated into a symbol which is white. 'U' or
	 * 'u' is translated into a symbol which is blue. 'B' or 'b' is translated
	 * into a symbol which is black. 'R' or 'r' is translated into a symbol
	 * which is red. 'G' or 'g' is translated into a symbol which is green.
	 * 
	 * Ex: "WW" creates two symbols which are white. Ex: "RG" creates a symbol
	 * which is red and a symbol which is green. Ex: "4U" creates a symbol which
	 * is 4 colorless and a symbol which is blue.
	 * 
	 * Symbols appearing within parentheses create a compound symbol. Ex: "(WB)"
	 * creates a symbol which is white or black. Ex: "(2R)" creates a symbol
	 * which is 2 colorless or red. Ex: "(10)" creates a symbol which is 10
	 * colorless.
	 * 
	 * Full example: "(2W)(2U)(2B)(2R)(2G)" creates 5 symbols: one which is 2
	 * colorless or white, one which is 2 colorless or blue, one which is 2
	 * colorless or black, one which is 2 colorless or red, and one which is 2
	 * colorless or green.
	 */
	public ManaPool(String poolString)
	{
		boolean compound = false;
		ManaSymbol compoundSymbol = null;
		this._delegate = new java.util.TreeSet<ManaSymbol>();
		this.originalOrder = new java.util.LinkedList<ManaSymbol>();

		poolString = poolString.toUpperCase();

		for(char c: poolString.toCharArray())
		{
			if(compound)
			{
				if(c == '(')
				{
					throw new UnsupportedOperationException("Invalid poolString syntax: '(' found within compound mana symbol");
				}
				else if(c == ')')
				{
					if(compoundSymbol.colorless > 0)
						compoundSymbol.name += compoundSymbol.colorless;
					compoundSymbol.name += ")";
					this.add(compoundSymbol);
					compoundSymbol = null;
					compound = false;
				}
				else if(('0' <= c) && (c <= '9'))
				{
					compoundSymbol.colorless = (compoundSymbol.colorless * 10) + (c - '0');
				}
				else if('W' == c)
				{
					compoundSymbol.name += "W";
					compoundSymbol.colors.add(Color.WHITE);
				}
				else if('U' == c)
				{
					compoundSymbol.name += "U";
					compoundSymbol.colors.add(Color.BLUE);
				}
				else if('B' == c)
				{
					compoundSymbol.name += "B";
					compoundSymbol.colors.add(Color.BLACK);
				}
				else if('R' == c)
				{
					compoundSymbol.name += "R";
					compoundSymbol.colors.add(Color.RED);
				}
				else if('G' == c)
				{
					compoundSymbol.name += "G";
					compoundSymbol.colors.add(Color.GREEN);
				}
				else if('X' == c)
				{
					compoundSymbol.name += "X";
					compoundSymbol.isX = true;
				}
				else if('P' == c)
				{
					compoundSymbol.name += "P";
					compoundSymbol.isPhyrexian = true;
				}
			}
			else
			{
				if(c == '(')
				{
					compound = true;
					compoundSymbol = new ManaSymbol("(");
				}
				else if(c == ')')
				{
					throw new UnsupportedOperationException("Invalid poolString syntax: ')' found without compound mana symbol");
				}
				else if(('0' <= c) && (c <= '9'))
				{
					ManaSymbol temp = new ManaSymbol(Character.toString(c));
					temp.colorless = c - '0';
					this.add(temp);
				}
				else if('W' == c)
				{
					this.add(new ManaSymbol(Color.WHITE));
				}
				else if('U' == c)
				{
					this.add(new ManaSymbol(Color.BLUE));
				}
				else if('B' == c)
				{
					this.add(new ManaSymbol(Color.BLACK));
				}
				else if('R' == c)
				{
					this.add(new ManaSymbol(Color.RED));
				}
				else if('G' == c)
				{
					this.add(new ManaSymbol(Color.GREEN));
				}
				else if('X' == c)
				{
					ManaSymbol temp = new ManaSymbol("X");
					temp.isX = true;
					this.add(temp);
				}
				else if('P' == c)
				{
					ManaSymbol temp = new ManaSymbol("P");
					temp.isPhyrexian = true;
					this.add(temp);
				}
			}
		}
	}

	@Override
	public boolean add(ManaSymbol arg0)
	{
		if(this._delegate.add(arg0))
		{
			if(!arg0.isX)
				return this.originalOrder.add(arg0);
			this.originalOrder.add(0, arg0);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(java.util.Collection<? extends ManaSymbol> arg0)
	{
		return this._delegate.addAll(arg0) && this.originalOrder.addAll(arg0);
	}

	@Override
	public void clear()
	{
		this._delegate.clear();
		this.originalOrder.clear();
	}

	@Override
	public java.util.Comparator<? super ManaSymbol> comparator()
	{
		return this._delegate.comparator();
	}

	@Override
	public boolean contains(Object arg0)
	{
		if(arg0 instanceof ManaSymbol)
			return this._delegate.contains(arg0);
		return false;
	}

	@Override
	public boolean containsAll(java.util.Collection<?> arg0)
	{
		return this._delegate.containsAll(arg0);
	}

	/** @return The amount of mana in this pool. */
	public int converted()
	{
		int size = 0;
		for(ManaSymbol m: this)
		{
			if(m.colorless > 0)
				size += m.colorless;
			else if(m.colors.size() > 0)
				size += 1;
		}
		return size;
	}

	/**
	 * Returns a mana pool that is all the mana in this pool duplicated a
	 * certain number of times.
	 * 
	 * @param howMany How many times to duplicate this pool.
	 * @return If '1' is specified, return this. Otherwise, return a ManaPool
	 * containing the specified number of copies of the ManaSymbols in this
	 * ManaPool.
	 */
	public ManaPool duplicate(int howMany)
	{
		if(howMany == 1)
			return this;

		ManaPool newPool = new ManaPool();
		for(ManaSymbol symbol: this)
			for(int i = 0; i < howMany; i++)
				newPool.add(symbol.create());
		return newPool;
	}

	@Override
	public int hashCode()
	{
		int ret = 31;
		for(ManaSymbol symbol: this)
		{
			ret += 29 * symbol.colorless;
			ret += symbol.colors.hashCode();
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof ManaPool))
			return false;

		ManaPool other = (ManaPool)obj;
		if(this.converted() != other.converted())
			return false;

		java.util.Collection<ManaSymbol> toBeChecked = new java.util.LinkedList<ManaSymbol>(other);
		// these keep track of the amount of "pure colorless" (that is, no 2/C
		// symbols) in each pool.
		int thisColorless = 0;
		int otherColorless = 0;
		outer: for(ManaSymbol symbol: this)
		{
			// pure colorless symbol
			if(symbol.colors.isEmpty())
			{
				thisColorless += symbol.colorless;
				continue;
			}

			// colored or X/C symbol; must match exactly
			for(java.util.Iterator<ManaSymbol> otherItr = toBeChecked.iterator(); otherItr.hasNext();)
			{
				ManaSymbol otherSymbol = otherItr.next();
				if(symbol.colorless != otherSymbol.colorless)
					continue;
				if(!symbol.colors.equals(otherSymbol.colors))
					continue;

				otherItr.remove();

				continue outer;
			}
			return false;
		}

		for(ManaSymbol symbol: other)
			if(symbol.colors.isEmpty())
				otherColorless += symbol.colorless;

		return thisColorless == otherColorless;
	}

	/**
	 * Expands any instance of an X-mana symbol in this mana pool to X 1-mana
	 * symbols. The new mana symbols have the isX field set to true.
	 * 
	 * @param X X.
	 * @param restriction What type of mana may be used to pay X. For generic,
	 * specify colorless.
	 * @return The expanded version of this mana pool.
	 */
	public ManaPool expandX(int X, ManaSymbol.ManaType restriction)
	{
		ManaPool ret = new ManaPool();
		java.util.Set<ManaSymbol> remove = new java.util.HashSet<ManaSymbol>();
		java.util.Set<ManaSymbol> setX = new java.util.HashSet<ManaSymbol>();
		for(ManaSymbol symbol: this)
		{
			if(symbol.isX)
			{
				setX.add(symbol);

				remove.add(symbol);
				for(int i = 0; i < X; i++)
				{
					ManaSymbol newSymbol = symbol.create();
					if(restriction == ManaSymbol.ManaType.COLORLESS)
						newSymbol.colorless = 1;
					else
					{
						newSymbol.colorless = 0;
						newSymbol.colors.add(restriction.getColor());
					}
					ret.add(newSymbol);
				}
			}
			else
				ret.add(symbol);
		}
		for(ManaSymbol symbol: setX)
			symbol.colorless = X;

		return ret;
	}

	/**
	 * Expands colorless mana symbols into groups of 1-colorless mana symbols,
	 * hybrid symbols into their respective colors, and phyrexian mana symbols
	 * into their respective colors and 2-life payments. This method will create
	 * a new {@link CostCollection} for each possible explosion of each hybrid
	 * symbol.
	 * 
	 * @param costType The cost type to use for the resulting CostCollections.
	 * @return The exploded mana pool.
	 */
	public final java.util.Set<CostCollection> explode(Object costType)
	{
		if(this.isEmpty())
			return java.util.Collections.singleton(new CostCollection(costType));

		// Keys are expansions of this pool prior to the symbol at i; values are
		// the amount of life to pay because of Phyrexian mana
		java.util.Map<ManaPool, Integer> expansions = new java.util.HashMap<ManaPool, Integer>();
		for(ManaSymbol poolSymbol: this)
		{
			// A mana symbol can be exploded into (a mana symbol with a single
			// color) or (one or more mana symbols with a single colorless) or
			// (a payment of life)
			ManaPool coloredSymbols = new ManaPool();
			ManaPool colorlessSymbols = new ManaPool();
			int lifePayment = 0;

			if(poolSymbol.isSingular())
				coloredSymbols.add(poolSymbol);
			else
			{
				for(Color color: poolSymbol.colors)
				{
					ManaSymbol s = poolSymbol.create();
					s.isPhyrexian = false;
					s.colors = java.util.EnumSet.of(color);
					s.colorless = 0;
					coloredSymbols.add(s);
				}
				for(int i = 0; i < poolSymbol.colorless; i++)
				{
					ManaSymbol s = poolSymbol.create();
					s.isPhyrexian = false;
					s.colors = java.util.EnumSet.noneOf(Color.class);
					s.colorless = 1;
					colorlessSymbols.add(s);
				}
			}
			if(poolSymbol.isPhyrexian)
				lifePayment += 2;

			if(expansions.isEmpty())
			{
				for(ManaSymbol colorSymbol: coloredSymbols)
				{
					ManaPool newKey = new ManaPool();
					newKey.add(colorSymbol);
					expansions.put(newKey, 0);
				}
				if(!colorlessSymbols.isEmpty())
					expansions.put(colorlessSymbols, 0);
				if(0 < lifePayment)
					expansions.put(new ManaPool(), lifePayment);
			}
			else
			{
				java.util.Map<ManaPool, Integer> newExpansions = new java.util.HashMap<ManaPool, Integer>();
				for(java.util.Map.Entry<ManaPool, Integer> oldExpansion: expansions.entrySet())
				{
					ManaPool oldKey = oldExpansion.getKey();
					int oldLifeLoss = oldExpansion.getValue();

					for(ManaSymbol colorSymbol: coloredSymbols)
					{
						ManaPool newKey = new ManaPool(oldKey);
						newKey.add(colorSymbol);
						newExpansions.put(newKey, oldLifeLoss);
					}
					if(!colorlessSymbols.isEmpty())
					{
						ManaPool newKey = new ManaPool(oldKey);
						newKey.addAll(colorlessSymbols);
						newExpansions.put(newKey, oldLifeLoss);
					}
					if(0 < lifePayment)
						newExpansions.put(oldKey, oldLifeLoss + lifePayment);
				}
				expansions = newExpansions;
			}
		}

		java.util.Set<CostCollection> ret = new java.util.HashSet<CostCollection>();
		for(java.util.Map.Entry<ManaPool, Integer> expansion: expansions.entrySet())
		{
			int lifePayment = expansion.getValue();
			ManaPool manaCost = expansion.getKey();

			if(0 < lifePayment)
			{
				EventFactory payLife = org.rnd.jmagic.Convenience.payLife(org.rnd.jmagic.engine.generators.You.instance(), lifePayment, "Pay " + lifePayment + " life");

				if(manaCost.isEmpty())
					ret.add(new CostCollection(costType, payLife));
				else
					ret.add(new CostCollection(costType, manaCost, payLife));
			}
			else if(!(manaCost.isEmpty()))
				ret.add(new CostCollection(costType, manaCost));
		}
		return ret;
	}

	@Override
	public ManaSymbol first()
	{
		return this._delegate.first();
	}

	public java.util.List<ManaSymbol> getDisplayOrder()
	{
		java.util.LinkedList<ManaSymbol> ret = new java.util.LinkedList<ManaSymbol>(this.originalOrder);
		java.util.Collections.reverse(ret);
		return ret;
	}

	@Override
	public java.util.SortedSet<ManaSymbol> headSet(ManaSymbol toElement)
	{
		return this._delegate.headSet(toElement);
	}

	/** @return Whether this ManaPool represents an empty mana pool. */
	@Override
	public boolean isEmpty()
	{
		for(ManaSymbol symbol: this)
			if(symbol.colors.size() > 0 || symbol.colorless > 0 || symbol.isPhyrexian || symbol.isX)
				return false;
		return true;
	}

	@Override
	public java.util.Iterator<ManaSymbol> iterator()
	{
		return this._delegate.iterator();
	}

	@Override
	public ManaSymbol last()
	{
		return this._delegate.last();
	}

	/**
	 * Ensures this mana pool has at least the specified amount of mana; if it
	 * doesn't, colorless mana is added to it until it does.
	 * 
	 * In other words, Trinisphere this mana pool up to the specified amount.
	 */
	public void minimum(int minimum)
	{
		int additional = minimum - this.converted();
		if(additional > 0)
			this.addAll(new ManaPool("(" + additional + ")"));
	}

	/**
	 * Determines whether the mana in this pool can pay a cost.
	 * 
	 * @param state The game state to use as context
	 * @param cost The cost to check.
	 * @return True if the mana in this pool can pay the mana in any explosion
	 * of the specified cost; false otherwise. (Note that if there is Phyrexian
	 * mana in the specified cost, they won't count.)
	 */
	public boolean pays(GameState state, ManaPool cost)
	{
		for(CostCollection singularCost: cost.explode(""))
			if(this.paysExplodedCost(state, singularCost.manaCost))
				return true;
		return false;
	}

	private boolean paysExplodedCost(GameState state, java.util.SortedSet<ManaSymbol> cost)
	{
		if(cost.isEmpty())
			return true;

		ManaSymbol costSymbol = cost.first();
		ManaPool remainingCost = new ManaPool(cost, costSymbol);

		for(ManaSymbol p: this)
		{
			if(p.pays(state, costSymbol))
			{
				ManaPool remainingPool = new ManaPool(this, p);
				if(remainingPool.paysExplodedCost(state, remainingCost))
					return true;
			}
		}

		// If none of the symbols in the pool pay for the first symbol in
		// the cost, the whole pool doesn't pay the whole cost
		return false;
	}

	/**
	 * Alters this ManaPool object by applying a cost reduction. It is assumed
	 * that all symbols in this mana pool are non-hybrid.
	 * 
	 * @param reduction The reduction to apply to this pool. In addition to
	 * symbols in this mana pool being non-hybrid, symbols in this parameter are
	 * also assumed to be non-hybrid. To apply a hybrid reduction, a player must
	 * first choose how to apply the reduction, as per: 117.7c If a cost is
	 * reduced by an amount of mana represented by a hybrid mana symbol, the
	 * player paying that cost chooses one half of that symbol at the time the
	 * cost reduction is applied. ...
	 */
	public void reduce(ManaPool reduction)
	{
		reductions: for(ManaSymbol r: reduction)
		{
			if(r.colors.size() == 0)
			{
				if(!r.colors.isEmpty())
					throw new UnsupportedOperationException("Can't reduce by a hybrid amount. The player paying the cost being reduced must make a choice first.");
				if(r.colorless == 0)
					continue;
				this.reduceColorless(r.colorless);
			}
			else if(r.colors.size() == 1)
			{
				Color reductionColor = r.colors.iterator().next();
				java.util.Iterator<ManaSymbol> tIter = this.iterator();
				while(tIter.hasNext())
				{
					ManaSymbol t = tIter.next();
					if(t.colors.contains(reductionColor))
					{
						tIter.remove();
						continue reductions;
					}
				}

				// If we've gotten to this point, no colored mana in the cost
				// matched. Reduce some colorless mana instead.
				this.reduceColorless(1);
			}
			else
			{
				throw new UnsupportedOperationException("Can't reduce by a hybrid amount. The player paying the cost being reduced must make a choice first.");
			}
		}
	}

	public void reduceColored(ManaPool reduction)
	{
		reductions: for(ManaSymbol r: reduction)
		{
			if(r.colors.size() > 1)
				throw new UnsupportedOperationException("Can't reduce by a hybrid amount. The player paying the cost being reduced must make a choice first.");
			if(r.colors.size() == 0)
				throw new UnsupportedOperationException("Applying a color-only cost reduction via a symbol with no colors...");

			Color color = r.colors.iterator().next();
			java.util.Iterator<ManaSymbol> iter = this.iterator();
			while(iter.hasNext())
			{
				ManaSymbol m = iter.next();
				if(m.colors.contains(color))
				{
					iter.remove();
					continue reductions;
				}
			}
		}
	}

	private void reduceColorless(int colorlessReduction)
	{
		java.util.Iterator<ManaSymbol> tIter = this.iterator();
		while(tIter.hasNext())
		{
			ManaSymbol t = tIter.next();

			if(t.isX)
			{
				// We should only get here when reducing costs with
				// their Xs expanded.
				if(!t.isSingular())
					throw new UnsupportedOperationException("Tried to reduce an unexpanded X cost.");

				tIter.remove();
				if(--colorlessReduction == 0)
					return;
			}

			// If this symbol is colored, it can't be reduced by a
			// generic amount.
			if(t.colors.size() != 0)
				continue;

			// if this symbol is bigger than the reduction, we can apply
			// the whole reduction to this symbol and move on to the
			// next reduction
			if(t.colorless > colorlessReduction)
			{
				t.colorless -= colorlessReduction;
				return;
			}

			// if it's exactly the same size, drop this cost symbol and
			// move on to the next reduction
			if(t.colorless == colorlessReduction)
			{
				tIter.remove();
				return;
			}

			// otherwise, apply as much of the reduction as we can to
			// this symbol (removing it from the pool) and try to keep
			// applying it to other symbols in this pool
			colorlessReduction -= t.colorless;
			tIter.remove();
		}
	}

	@Override
	public boolean remove(Object arg0)
	{
		return this._delegate.remove(arg0) && this.originalOrder.remove(arg0);
	}

	@Override
	public boolean removeAll(java.util.Collection<?> arg0)
	{
		return this._delegate.removeAll(arg0) && this.originalOrder.removeAll(arg0);
	}

	@Override
	public boolean retainAll(java.util.Collection<?> arg0)
	{
		return this._delegate.retainAll(arg0) && this.originalOrder.retainAll(arg0);
	}

	@Override
	public int size()
	{
		return this._delegate.size();
	}

	@Override
	public java.util.SortedSet<ManaSymbol> subSet(ManaSymbol fromElement, ManaSymbol toElement)
	{
		return this._delegate.subSet(fromElement, toElement);
	}

	@Override
	public java.util.SortedSet<ManaSymbol> tailSet(ManaSymbol fromElement)
	{
		return this._delegate.tailSet(fromElement);
	}

	@Override
	public Object[] toArray()
	{
		return this._delegate.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return this._delegate.toArray(arg0);
	}

	@Override
	public String toString()
	{
		if(this.isEmpty())
			return "(0)";

		StringBuilder ret = new StringBuilder();
		int colorless = 0;
		for(ManaSymbol m: this.originalOrder)
		{
			if(m.colors.isEmpty() && m.colorless > 0)
				colorless += m.colorless;
			else
				ret.append(m);
		}
		if(colorless != 0)
			ret.insert(0, "(" + colorless + ")");
		return ret.toString();
	}

	public boolean usesX()
	{
		for(ManaSymbol mana: this)
			if(mana.isX)
				return true;
		return false;
	}
}
