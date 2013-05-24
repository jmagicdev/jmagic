package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Skithiryx, the Blight Dragon")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON, SubType.DRAGON})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class SkithiryxtheBlightDragon extends Card
{
	public static final class SkithiryxtheBlightDragonAbility2 extends ActivatedAbility
	{
		public SkithiryxtheBlightDragonAbility2(GameState state)
		{
			super(state, "(B): Skithiryx, the Blight Dragon gains haste until end of turn.");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(createFloatingEffect("Skithiryx, the Blight Dragon gains haste until end of turn.", addAbilityToObject(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Haste.class)));
		}
	}

	public static final class SkithiryxtheBlightDragonAbility3 extends ActivatedAbility
	{
		public SkithiryxtheBlightDragonAbility3(GameState state)
		{
			super(state, "(B)(B): Regenerate Skithiryx.");
			this.setManaCost(new ManaPool("(B)(B)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Skithiryx."));
		}
	}

	public SkithiryxtheBlightDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// (B): Skithiryx, the Blight Dragon gains haste until end of turn.
		this.addAbility(new SkithiryxtheBlightDragonAbility2(state));

		// (B)(B): Regenerate Skithiryx.
		this.addAbility(new SkithiryxtheBlightDragonAbility3(state));
	}
}
