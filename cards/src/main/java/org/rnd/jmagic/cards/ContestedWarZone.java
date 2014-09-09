package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Contested War Zone")
@Types({Type.LAND})
@ColorIdentity({})
public final class ContestedWarZone extends Card
{
	public static final class ContestedWarZoneAbility0 extends EventTriggeredAbility
	{
		public ContestedWarZoneAbility0(GameState state)
		{
			super(state, "Whenever a creature deals combat damage to you, that creature's controller gains control of Contested War Zone.");

			this.addPattern(new SimpleDamagePattern(HasType.instance(Type.CREATURE), You.instance(), true));

			SetGenerator thatCreature = SourceOfDamage.instance(TriggerDamage.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, ControllerOf.instance(thatCreature));
			this.addEffect(createFloatingEffect(Empty.instance(), "That creature's controller gains control of Contested War Zone.", part));
		}
	}

	public static final class ContestedWarZoneAbility2 extends ActivatedAbility
	{
		public ContestedWarZoneAbility2(GameState state)
		{
			super(state, "(1), (T): Attacking creatures get +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addEffect(ptChangeUntilEndOfTurn(Attacking.instance(), +1, +0, "Attacking creatures get +1/+0 until end of turn."));
		}
	}

	public ContestedWarZone(GameState state)
	{
		super(state);

		// Whenever a creature deals combat damage to you, that creature's
		// controller gains control of Contested War Zone.
		this.addAbility(new ContestedWarZoneAbility0(state));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1), (T): Attacking creatures get +1/+0 until end of turn.
		this.addAbility(new ContestedWarZoneAbility2(state));
	}
}
