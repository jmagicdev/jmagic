package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul of Ravnica")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class SoulofRavnica extends Card
{
	public static final class SoulofRavnicaAbility1 extends ActivatedAbility
	{
		public SoulofRavnicaAbility1(GameState state)
		{
			super(state, "(5)(U)(U): Draw a card for each color among permanents you control.");
			this.setManaCost(new ManaPool("(5)(U)(U)"));

			SetGenerator colors = ColorsOf.instance(ControlledBy.instance(You.instance()));
			this.addEffect(drawCards(You.instance(), colors, "Draw a card for each color among permanents you control."));
		}
	}

	public static final class SoulofRavnicaAbility2 extends ActivatedAbility
	{
		public SoulofRavnicaAbility2(GameState state)
		{
			super(state, "(5)(U)(U), Exile Soul of Ravnica from your graveyard: Draw a card for each color among permanents you control.");
			this.setManaCost(new ManaPool("(5)(U)(U)"));
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Soul of Ravnica from your graveyard"));
			this.activateOnlyFromGraveyard();

			SetGenerator colors = ColorsOf.instance(ControlledBy.instance(You.instance()));
			this.addEffect(drawCards(You.instance(), colors, "Draw a card for each color among permanents you control."));
		}
	}

	public SoulofRavnica(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (5)(U)(U): Draw a card for each color among permanents you control.
		this.addAbility(new SoulofRavnicaAbility1(state));

		// (5)(U)(U), Exile Soul of Ravnica from your graveyard: Draw a card for
		// each color among permanents you control.
		this.addAbility(new SoulofRavnicaAbility2(state));
	}
}
