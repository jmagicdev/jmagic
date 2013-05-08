package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Insatiable Souleater")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class InsatiableSouleater extends Card
{
	public static final class InsatiableSouleaterAbility0 extends ActivatedAbility
	{
		public InsatiableSouleaterAbility0(GameState state)
		{
			super(state, "(g/p): Insatiable Souleater gains trample until end of turn.");
			this.setManaCost(new ManaPool("(g/p)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Trample.class, "Insatiable Souleater gains trample until end of turn."));
		}
	}

	public InsatiableSouleater(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);

		// (g/p): Insatiable Souleater gains trample until end of turn. ((g/p)
		// can be paid with either (G) or 2 life.)
		this.addAbility(new InsatiableSouleaterAbility0(state));
	}
}
