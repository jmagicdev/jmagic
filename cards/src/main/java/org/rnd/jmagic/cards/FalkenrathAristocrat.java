package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Falkenrath Aristocrat")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2BR")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class FalkenrathAristocrat extends Card
{
	public static final class FalkenrathAristocratAbility1 extends ActivatedAbility
	{
		public FalkenrathAristocratAbility1(GameState state)
		{
			super(state, "Sacrifice a creature: Falkenrath Aristocrat gains indestructible until end of turn. If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Aristocrat.");

			EventFactory sacrifice = sacrificeACreature();
			this.addCost(sacrifice);

			this.addEffect(createFloatingEffect("Falkenrath Aristocrat gains indestructible until end of turn.", addAbilityToObject(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Indestructible.class)));

			SetGenerator deadCreature = OldObjectOf.instance(EffectResult.instance(sacrifice));
			SetGenerator itWasAHuman = EvaluatePattern.instance(new SubTypePattern(SubType.HUMAN), deadCreature);
			this.addEffect(ifThen(itWasAHuman, putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Falkenrath Aristocrat."), "If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Torturer."));
		}
	}

	public FalkenrathAristocrat(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Sacrifice a creature: Falkenrath Aristocrat is indestructible this
		// turn. If the sacrificed creature was a Human, put a +1/+1 counter on
		// Falkenrath Aristocrat.
		this.addAbility(new FalkenrathAristocratAbility1(state));
	}
}
