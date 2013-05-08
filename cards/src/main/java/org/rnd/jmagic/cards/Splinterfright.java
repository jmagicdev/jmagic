package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Splinterfright")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Splinterfright extends Card
{
	public static final class SplinterfrightAbility1 extends CharacteristicDefiningAbility
	{
		public SplinterfrightAbility1(GameState state)
		{
			super(state, "Splinterfright's power and toughness are each equal to the number of creature cards in your graveyard.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class SplinterfrightAbility2 extends EventTriggeredAbility
	{
		public SplinterfrightAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, put the top two cards of your library into your graveyard.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(millCards(You.instance(), 2, "Put the top two cards of your library into your graveyard."));
		}
	}

	public Splinterfright(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Splinterfright's power and toughness are each equal to the number of
		// creature cards in your graveyard.
		this.addAbility(new SplinterfrightAbility1(state));

		// At the beginning of your upkeep, put the top two cards of your
		// library into your graveyard.
		this.addAbility(new SplinterfrightAbility2(state));
	}
}
