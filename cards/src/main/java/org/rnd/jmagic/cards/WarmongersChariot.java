package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warmonger's Chariot")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class WarmongersChariot extends Card
{
	public static final class WarmongersChariotAbility0 extends StaticAbility
	{
		public WarmongersChariotAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +2, +2));
		}
	}

	public static final class WarmongersChariotAbility1 extends StaticAbility
	{
		public WarmongersChariotAbility1(GameState state)
		{
			super(state, "As long as equipped creature has defender, it can attack as though it didn't have defender.");

			SetGenerator equippedHasDefender = Intersect.instance(EquippedBy.instance(This.instance()), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class));
			this.canApply = Both.instance(this.canApply, equippedHasDefender);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACK_AS_THOUGH_DOESNT_HAVE_DEFENDER);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EquippedBy.instance(This.instance()));
			this.addEffectPart(part);
		}
	}

	public WarmongersChariot(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2.
		this.addAbility(new WarmongersChariotAbility0(state));

		// As long as equipped creature has defender, it can attack as though it
		// didn't have defender.
		this.addAbility(new WarmongersChariotAbility1(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
