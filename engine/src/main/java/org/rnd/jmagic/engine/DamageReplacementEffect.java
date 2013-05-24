package org.rnd.jmagic.engine;

/**
 * Represents a replacement effect that applies to damage. Extend this class and
 * override match and replace in order to create prevention effects and other
 * damage replacement effects.
 * 
 * When extending this class, be careful that your effect is modifiable by
 * text-change effects. This is accomplished by using a SetGenerator whenever an
 * enum is referred to (color, for example). If this is clear as mud, look at
 * Luminesce. Rather than just checking the damage sources for RED and BLACK, it
 * stores a SetGenerator (really just a Identity) containing those colors, then
 * evaluates the generator when actually doing the match. Since evaluating a
 * SetGenerator applies text change effects to the evaluation, if any text
 * change effects are changing Luminesce, the colors to match against will be
 * correctly updated.
 */
public abstract class DamageReplacementEffect extends ReplacementEffect
{
	private boolean isPreventionEffect;
	private boolean isRedirectionEffect;
	public DamageAssignment.Batch isReplacing;

	/**
	 * Constructs a replacement effect that applies to no damage and does
	 * nothing when applied.
	 * 
	 * @param game The game in which the effect is to exist.
	 * @param name The text of the effect.
	 */
	public DamageReplacementEffect(Game game, String name)
	{
		super(game, name);
		this.isReplacing = new DamageAssignment.Batch();
		this.isPreventionEffect = false;
		this.isRedirectionEffect = false;
	}

	/**
	 * Applies this replacement effect to the specified damage batch. If the
	 * effect is optional, this involves asking the appropriate player whether
	 * to perform the replacement.
	 * 
	 * @param damageAssignments IN: the damage to replace; OUT: the replaced
	 * damage (possibly empty if the damage is prevented).
	 * @param chooser Who will choose which damage this effect applies to if
	 * appropriate, in accordance with 615.7.
	 * @return Other events to perform as a result of replacing the damage.
	 */
	public final int apply(DamageAssignment.Batch damageAssignments, Player chooser, java.util.List<EventFactory> events)
	{
		this.isReplacing = damageAssignments;
		int sentToPrevent = 0;

		// 615.11. Some effects state that damage "can't be prevented." If
		// unpreventable damage would be dealt, any applicable prevention
		// effects are still applied to it. Those effects won't prevent any
		// damage, but any additional effects they have will take place.
		// Existing damage prevention shields won't be reduced by damage that
		// can't be prevented.
		DamageAssignment.Batch preventable = new DamageAssignment.Batch();
		for(DamageAssignment a: damageAssignments)
		{
			a.replacedBy.add(this);
			if(this.isPreventionEffect() && !a.isUnpreventable)
				preventable.add(a);
		}

		if(this.apply(this.game.actualState))
		{
			DamageAssignment.Batch toReplace = new DamageAssignment.Batch(damageAssignments);
			DamageAssignment.Batch untouched = new DamageAssignment.Batch(damageAssignments);

			// 615.7. ... If damage would be dealt to the shielded creature or
			// player by two or more applicable sources at the same time, the
			// player or the controller of the creature chooses which damage the
			// shield prevents.
			FloatingContinuousEffect fce = this.getFloatingContinuousEffect(this.game.actualState);
			if(fce != null && fce.damage != -1 && fce.damage < toReplace.size())
			{
				if(toReplace.allTheSame())
				{
					java.util.Iterator<DamageAssignment> iter = toReplace.iterator();
					int i = fce.damage;
					while(iter.hasNext())
					{
						iter.next();
						if(i == 0)
							iter.remove();
						else
							i--;
					}
				}
				else
				{
					java.util.List<DamageAssignment> chosen = chooser.sanitizeAndChoose(this.game.actualState, fce.damage, toReplace, PlayerInterface.ChoiceType.DAMAGE, PlayerInterface.ChooseReason.REPLACE_WHICH_DAMAGE);
					toReplace.retainAll(chosen);
				}
			}
			untouched.removeAll(toReplace);
			preventable.retainAll(toReplace);

			events.addAll(this.replace(toReplace));
			if(this.isPreventionEffect())
			{
				sentToPrevent = preventable.size();
				toReplace.removeAll(preventable);
				events.addAll(this.prevent(preventable));
				toReplace.addAll(preventable);
			}
			if(this.isRedirectionEffect())
			{
				// 614.9. Some effects replace damage dealt to one creature,
				// planeswalker, or player with the same damage dealt to another
				// creature, planeswalker, or player; such effects are called
				// redirection effects. If either creature or planeswalker is no
				// longer on the battlefield when the damage would be
				// redirected, or is no longer a creature or planeswalker when
				// the damage would be redirected, the effect does nothing. If
				// damage would be redirected to or from a player who has left
				// the game, the effect does nothing.
				java.util.Map<DamageAssignment, DamageAssignment> redirections = new java.util.HashMap<DamageAssignment, DamageAssignment>();
				for(DamageAssignment assignment: toReplace)
				{
					Identified originalTaker = this.game.actualState.get(assignment.takerID);
					if(originalTaker.isGameObject())
					{
						GameObject takerObject = (GameObject)originalTaker;
						if(takerObject.isGhost() || takerObject.zoneID != this.game.actualState.battlefield().ID || !(takerObject.getTypes().contains(Type.CREATURE) || takerObject.getTypes().contains(Type.PLANESWALKER)))
							continue;
					}
					else if(originalTaker.isPlayer())
					{
						if(((Player)originalTaker).outOfGame)
							continue;
					}
					else
						throw new RuntimeException("Non-Player, Non-GameObject is taking damage...");
					redirections.put(assignment, null);
				}
				events.addAll(this.redirect(redirections));
				for(java.util.Map.Entry<DamageAssignment, DamageAssignment> redirection: redirections.entrySet())
				{
					DamageAssignment from = redirection.getKey();
					DamageAssignment to = redirection.getValue();
					if(to != null)
					{
						Identified newTaker = this.game.actualState.get(to.takerID);
						if(newTaker.isGameObject())
						{
							GameObject takerObject = (GameObject)newTaker;
							if(takerObject.isGhost() || takerObject.zoneID != this.game.actualState.battlefield().ID || !(takerObject.getTypes().contains(Type.CREATURE) || takerObject.getTypes().contains(Type.PLANESWALKER)))
								continue;
						}
						else if(newTaker.isPlayer())
						{
							if(((Player)newTaker).outOfGame)
								continue;
						}
						else
							throw new RuntimeException("Non-Player, Non-GameObject is taking damage...");
						toReplace.remove(from);

						to.replacedBy.addAll(from.replacedBy);
						to.replacedBy.add(this);
						toReplace.add(to);
					}
				} // for each redirection
			} // this is a redirection effect
			damageAssignments.clear();
			damageAssignments.addAll(toReplace);
			damageAssignments.addAll(untouched);
		} // if this was applied

		return sentToPrevent;
	}

	public boolean isPreventionEffect()
	{
		return this.isPreventionEffect;
	}

	public boolean isRedirectionEffect()
	{
		return this.isRedirectionEffect;
	}

	protected void makePreventionEffect()
	{
		this.isPreventionEffect = true;
	}

	protected void makeRedirectionEffect()
	{
		this.isRedirectionEffect = true;
	}

	/**
	 * Determines whether this effect should apply to any of the specified
	 * damage.
	 * 
	 * @param context The event containing the damage assignments to match
	 * against
	 * @param damageAssignments The damage to check against.
	 * @return Batch of damage this effect matches against.
	 */
	public abstract DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments);

	/**
	 * Uses this effect to prevent the specified damage batch. The calling
	 * method is responsible for marking that this effect replaced the batch.
	 * 
	 * @param damageAssignments IN: the damage to prevent; OUT: the remaining
	 * damage (possibly empty if all the damage is prevented).
	 * @return Other events to perform as a result of preventing the damage.
	 */
	public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
	{
		throw new RuntimeException("Prevention effect failed to declare prevent(DamageAssignment.Batch)");
	}

	/**
	 * Uses this effect to redirect the specified damage batch. The calling
	 * method is responsible for marking that this effect replaced the batch. If
	 * this is a prevention effect, this method shouldn't change the
	 * damageAssignments collection at all. Bad things could happen.
	 * 
	 * @param damageAssignments IN: a map containing the damage to replace as
	 * keys to null values; OUT: a map containing the damage to replace as keys
	 * to the damage replacing it
	 * @return Other events to perform as a result of redirecting the damage.
	 */
	public java.util.List<EventFactory> redirect(java.util.Map<DamageAssignment, DamageAssignment> damageAssignments)
	{
		throw new RuntimeException("Redirection effect failed to declare redirect(Map<DamageAssignment, DamageAssignment>)");
	}

	/**
	 * Overriding this method <i>probably</i> isn't necessary -- most things
	 * that a damage replacement effect refers to are things that deal or take
	 * damage. Anything taking damage must be a permanent (or player) and is
	 * thus covered by that category in rule 609.7a; anything dealing damage is
	 * either a permanent (if it's dealing combat damage) or spell, or will
	 * <i>most likely</i> be referred to by an ability causing that object to
	 * deal damage. One example where this method must be overridden is
	 * {@link org.rnd.jmagic.cards.RefractionTrap} -- the replacement effect
	 * itself deals damage, and thus the Refraction Trap that effect refers to
	 * isn't covered by any of the other categories in rule 609.7a.
	 */
	@Override
	public java.util.Collection<GameObject> refersTo(GameState state)
	{
		return java.util.Collections.emptySet();
	}

	/**
	 * Uses this effect to replace the specified damage batch. The calling
	 * method is responsible for marking that this effect replaced the batch. If
	 * this is a prevention effect, this method shouldn't change the
	 * damageAssignments collection at all. Bad things could happen.
	 * 
	 * @param damageAssignments IN: the damage to replace; OUT: the replaced
	 * damage.
	 * @return Other events to perform as a result of replacing the damage.
	 */
	public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
	{
		// Prevention and Redirection effects won't replace damage, for the most
		// part. (Weird cards like protean hydra will still use it though)
		return new java.util.LinkedList<EventFactory>();
	}
}
