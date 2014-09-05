package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Chimeric Mass")
@Types({Type.ARTIFACT})
@ManaCost("X")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class ChimericMass extends Card
{
	public static final class ChimericMassAbility1 extends ActivatedAbility
	{
		public static final class ChimericCDA extends CharacteristicDefiningAbility
		{
			public ChimericCDA(GameState state)
			{
				super(state, "This creature's power and toughness are each equal to the number of charge counters on it.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

				SetGenerator number = Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.CHARGE));

				this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
			}
		}

		public ChimericMassAbility1(GameState state)
		{
			super(state, "(1): Until end of turn, Chimeric Mass becomes a Construct artifact creature with \"This creature's power and toughness are each equal to the number of charge counters on it.\"");
			this.setManaCost(new ManaPool("(1)"));

			ContinuousEffect.Part types = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			types.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			types.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.CONSTRUCT, Type.ARTIFACT, Type.CREATURE));

			this.addEffect(createFloatingEffect("Until end of turn, Chimeric Mass becomes a Construct artifact creature with \"This creature's power and toughness are each equal to the number of charge counters on it.\"", types, addAbilityToObject(ABILITY_SOURCE_OF_THIS, ChimericMassAbility1.ChimericCDA.class)));
		}
	}

	public ChimericMass(GameState state)
	{
		super(state);

		// Chimeric Mass enters the battlefield with X charge counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X charge counters on it", Counter.CounterType.CHARGE));

		// (1): Until end of turn, Chimeric Mass becomes a Construct artifact
		// creature with "This creature's power and toughness are each equal to
		// the number of charge counters on it."
		this.addAbility(new ChimericMassAbility1(state));
	}
}
