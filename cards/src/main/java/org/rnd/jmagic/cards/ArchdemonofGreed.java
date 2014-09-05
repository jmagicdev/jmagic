package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Archdemon of Greed")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ArchdemonofGreed extends AlternateCard
{
	public static final class ArchdemonofGreedAbility1 extends EventTriggeredAbility
	{
		public ArchdemonofGreedAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacrifice = sacrifice(You.instance(), 1, HasSubType.instance(SubType.HUMAN), "Sacrifice a Human");

			this.addEffect(ifElse(sacrifice, sequence(tap(ABILITY_SOURCE_OF_THIS, "Tap Archdemon of Greed."), permanentDealDamage(9, You.instance(), "It deals 9 damage to you.")), "Sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you."));
		}
	}

	public ArchdemonofGreed(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		this.setColorIndicator(Color.BLACK);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// At the beginning of your upkeep, sacrifice a Human. If you can't, tap
		// Archdemon of Greed and it deals 9 damage to you.
		this.addAbility(new ArchdemonofGreedAbility1(state));
	}
}
