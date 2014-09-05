package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dread Slaver")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.ZOMBIE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DreadSlaver extends Card
{
	public static final class DreadSlaverAbility0 extends EventTriggeredAbility
	{
		public DreadSlaverAbility0(GameState state)
		{
			super(state, "Whenever a creature dealt damage by Dread Slaver this turn dies, return it to the battlefield under your control. That creature is a black Zombie in addition to its other colors and types.");

			SetGenerator thisCreature = ABILITY_SOURCE_OF_THIS;
			SetGenerator damagedByThis = DealtDamageByThisTurn.instance(thisCreature);

			this.addPattern(whenXDies(damagedByThis));

			SetGenerator deadCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			EventFactory putBack = putOntoBattlefield(deadCreature, "Return it to the battlefield under your control.");
			this.addEffect(putBack);

			SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(putBack));

			ContinuousEffect.Part color = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			color.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));
			color.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);

			ContinuousEffect.Part type = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			type.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ZOMBIE));
			type.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);

			this.addEffect(createFloatingEffect(Empty.instance(), "That creature is a black Zombie in addition to its other colors and types.", color, type));

			state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
		}
	}

	public DreadSlaver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Whenever a creature dealt damage by Dread Slaver this turn dies,
		// return it to the battlefield under your control. That creature is a
		// black Zombie in addition to its other colors and types.
		this.addAbility(new DreadSlaverAbility0(state));
	}
}
