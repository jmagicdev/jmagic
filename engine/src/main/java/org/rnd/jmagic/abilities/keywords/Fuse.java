package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Fuse extends Keyword
{
	public Fuse(GameState state)
	{
		super(state, "Fuse");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<>();
		ret.add(new FuseCastAbility(this.state, this));
		return ret;
	}

	public static final class FuseCastAbility extends StaticAbility
	{
		public FuseCastAbility(GameState state, Fuse parent)
		{
			super(state, "You may cast both one or both halves of this card from your hand.");

			this.canApply = NonEmpty.instance();

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new Fuse.FuseAction.Factory()));
			this.addEffectPart(part);
		}
	}

	public static class FuseAction extends CastSpellAction
	{
		private static class Factory extends SpecialActionFactory
		{
			@Override
			public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
			{
				if(!source.getOwner(state).equals(state.getPlayerWithPriority()))
					return java.util.Collections.<PlayerAction>emptySet();

				java.util.Set<PlayerAction> ret = new java.util.HashSet<PlayerAction>();

				// Only when you could begin casting each half of the spell
				boolean castLeft = false;
				boolean castRight = false;
				for(PlayerAction action: state.playerActions)
					if(action instanceof CastSpellAction)
					{
						CastSpellAction cast = (CastSpellAction)action;
						if(cast.toBePlayedID == source.ID)
						{
							if(cast.characteristicsIndices[0] == 0)
							{
								castLeft = true;
								if(castRight)
									break;
							}
							else if(cast.characteristicsIndices[0] == 1)
							{
								castRight = true;
								if(castLeft)
									break;
							}
						}
					}

				if(castLeft && castRight)
					ret.add(getAction(state, source, actor));

				return ret;
			}

			private FuseAction getAction(GameState state, GameObject source, Player actor)
			{
				return new FuseAction(state.game, source, actor);
			}
		}

		public FuseAction(Game game, GameObject cast, Player casting)
		{
			// all the magic is in the {0, 1} here
			super(game, cast, new int[] {0, 1}, casting, cast.ID);
			this.name = "Cast " + cast;
		}
	}
}
