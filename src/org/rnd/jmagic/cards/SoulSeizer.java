package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul Seizer")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
@BackFace(GhastlyHaunting.class)
public final class SoulSeizer extends Card
{
	public static final class SoulSeizerAbility1 extends EventTriggeredAbility
	{
		public SoulSeizerAbility1(GameState state)
		{
			super(state, "When Soul Seizer deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(thatPlayer)), "target creature that player controls"));

			EventFactory transform = youMay(transform(ABILITY_SOURCE_OF_THIS, "Soul Seizer"));
			EventFactory attach = attach(ABILITY_SOURCE_OF_THIS, target, "Attach it to target creature that player controls.");
			this.addEffect(ifThen(transform, attach, "You may transform it. If you do, attach it to target creature that player controls."));
		}
	}

	public SoulSeizer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Soul Seizer deals combat damage to a player, you may transform
		// it. If you do, attach it to target creature that player controls.
		this.addAbility(new SoulSeizerAbility1(state));
	}
}
