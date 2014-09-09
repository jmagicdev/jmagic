package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glacial Chasm")
@Types({Type.LAND})
@ColorIdentity({})
public final class GlacialChasm extends Card
{
	public static final class ETBSac extends EventTriggeredAbility
	{
		public ETBSac(GameState state)
		{
			super(state, "When Glacial Chasm enters the battlefield, sacrifice a land.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(sacrifice(You.instance(), 1, HasType.instance(Type.LAND), "Sacrifice a land"));
		}
	}

	public static final class YouCantAttack extends StaticAbility
	{
		public YouCantAttack(GameState state)
		{
			super(state, "Creatures you control can't attack.");
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Attacking.instance(), ControlledBy.instance(You.instance()))));
			this.addEffectPart(part);
		}
	}

	public static final class PreventDamageToYouEffect extends DamageReplacementEffect
	{
		public PreventDamageToYouEffect(Game game)
		{
			super(game, "Prevent all damage that would be dealt to you");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);

			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment assignment: damageAssignments)
				if(assignment.takerID == you.ID)
					ret.add(assignment);
			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return new java.util.LinkedList<EventFactory>();
		}
	}

	public static final class PreventDamageToYou extends StaticAbility
	{
		public PreventDamageToYou(GameState state)
		{
			super(state, "Prevent all damage that would be dealt to you.");

			this.addEffectPart(replacementEffectPart(new PreventDamageToYouEffect(state.game)));
		}
	}

	public static final class CumulativeUpkeepPay2Life extends org.rnd.jmagic.abilities.keywords.CumulativeUpkeep
	{
		public CumulativeUpkeepPay2Life(GameState state)
		{
			super(state, "Pay 2 life.");
		}

		@Override
		protected EventFactory getFactory(SetGenerator thisAbility)
		{
			return payLife(You.instance(), 2, "Pay 2 life");
		}
	}

	public GlacialChasm(GameState state)
	{
		super(state);

		// Cumulative upkeep\u2014Pay 2 life. (At the beginning of your upkeep,
		// put an age counter on this permanent, then sacrifice it unless you
		// pay its upkeep cost for each age counter on it.)
		this.addAbility(new CumulativeUpkeepPay2Life(state));

		// When Glacial Chasm enters the battlefield, sacrifice a land.
		this.addAbility(new ETBSac(state));

		// Creatures you control can't attack.
		this.addAbility(new YouCantAttack(state));

		// Prevent all damage that would be dealt to you.
		this.addAbility(new PreventDamageToYou(state));
	}
}
