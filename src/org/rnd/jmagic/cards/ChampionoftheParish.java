package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Champion of the Parish")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ChampionoftheParish extends Card
{
	public static final class ChampionoftheParishAbility0 extends EventTriggeredAbility
	{
		public ChampionoftheParishAbility0(GameState state)
		{
			super(state, "Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Champion of the Parish.");

			SetGenerator anotherHuman = RelativeComplement.instance(HasSubType.instance(SubType.HUMAN), ABILITY_SOURCE_OF_THIS);
			SetGenerator yourControl = ControlledBy.instance(You.instance());
			SetGenerator anotherHumanUnderYourControl = Intersect.instance(anotherHuman, yourControl);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), anotherHumanUnderYourControl, false));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Champion of the Parish."));
		}
	}

	public ChampionoftheParish(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever another Human enters the battlefield under your control, put
		// a +1/+1 counter on Champion of the Parish.
		this.addAbility(new ChampionoftheParishAbility0(state));
	}
}
