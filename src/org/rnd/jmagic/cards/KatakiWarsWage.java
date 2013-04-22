package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kataki, War's Wage")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.SAVIORS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class KatakiWarsWage extends Card
{
	public static final class KatakiWarsWageAbility0 extends StaticAbility
	{
		public static final class Granted extends EventTriggeredAbility
		{
			public Granted(GameState state)
			{
				super(state, "At the beginning of your upkeep, sacrifice this artifact unless you pay (1).");
				this.addPattern(atTheBeginningOfYourUpkeep());

				EventFactory payMana = new EventFactory(EventType.PAY_MANA, "Pay (1).");
				payMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
				payMana.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1")));
				payMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
				payMana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
				this.addEffect(unless(You.instance(), sacrificeThis("this artifact"), payMana, "Sacrifice this artifact unless you pay (1)."));
			}
		}

		public KatakiWarsWageAbility0(GameState state)
		{
			super(state, "All artifacts have \"At the beginning of your upkeep, sacrifice this artifact unless you pay (1).\"");

			this.addEffectPart(addAbilityToObject(ArtifactPermanents.instance(), Granted.class));
		}
	}

	public KatakiWarsWage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// All artifacts have "At the beginning of your upkeep, sacrifice this
		// artifact unless you pay (1)."
		this.addAbility(new KatakiWarsWageAbility0(state));
	}
}
