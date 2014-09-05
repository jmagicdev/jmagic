package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Steel Hellkite")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.DRAGON})
@ManaCost("6")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SteelHellkite extends Card
{
	public static final class SteelHellkiteAbility1 extends ActivatedAbility
	{
		public SteelHellkiteAbility1(GameState state)
		{
			super(state, "(2): Steel Hellkite gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(2)"));
			this.addEffect(createFloatingEffect("Steel Hellkite gets +1/+0 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +1, +0)));
		}
	}

	public static final class SteelHellkiteAbility2 extends ActivatedAbility
	{
		public SteelHellkiteAbility2(GameState state)
		{
			super(state, "(X): Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by Steel Hellkite this turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(X)"));

			state.ensureTracker(new DealtDamageByThisTurn.DealtCombatDamageByTracker());

			SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), LandPermanents.instance());
			SetGenerator cmcX = HasConvertedManaCost.instance(ValueOfX.instance(This.instance()));
			SetGenerator whoseControllerWasDealtCombatDamage = ControlledBy.instance(Intersect.instance(Players.instance(), DealtDamageByThisTurn.instance(ABILITY_SOURCE_OF_THIS, true)));
			SetGenerator destroy = Intersect.instance(nonlandPermanents, cmcX, whoseControllerWasDealtCombatDamage);

			this.addEffect(destroy(destroy, "Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by Steel Hellkite this turn. Activate this ability only once each turn."));
		}
	}

	public SteelHellkite(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (2): Steel Hellkite gets +1/+0 until end of turn.
		this.addAbility(new SteelHellkiteAbility1(state));

		// (X): Destroy each nonland permanent with converted mana cost X whose
		// controller was dealt combat damage by Steel Hellkite this turn.
		// Activate this ability only once each turn.
		this.addAbility(new SteelHellkiteAbility2(state));
	}
}
