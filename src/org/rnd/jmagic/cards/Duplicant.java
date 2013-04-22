package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duplicant")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class Duplicant extends Card
{
	public static final class DuplicantAbility0 extends EventTriggeredAbility
	{
		public DuplicantAbility0(GameState state)
		{
			super(state, "When Duplicant enters the battlefield, you may exile target nontoken creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance()), "target nontoken creature"));
			EventFactory exile = exile(target, "Exile target nontoken creature");
			exile.setLink(this);
			this.addEffect(youMay(exile, "You may exile target nontoken creature."));

			this.getLinkManager().addLinkClass(DuplicantAbility1.class);
		}
	}

	public static final class DuplicantAbility1 extends StaticAbility
	{
		public DuplicantAbility1(GameState state)
		{
			super(state, "As long as the exiled card is a creature card, Duplicant has that card's power, toughness, and creature types. It's still a Shapeshifter.");
			this.getLinkManager().addLinkClass(DuplicantAbility0.class);

			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator isACreature = Intersect.instance(exiledCard, HasType.instance(Type.CREATURE));
			this.canApply = Both.instance(this.canApply, isACreature);

			this.addEffectPart(setPowerAndToughness(This.instance(), PowerOf.instance(exiledCard), ToughnessOf.instance(exiledCard)));
			this.addEffectPart(addType(This.instance(), SubTypesOf.instance(exiledCard, Type.CREATURE)));
		}
	}

	public Duplicant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Imprint \u2014 When Duplicant enters the battlefield, you may exile
		// target nontoken creature.
		this.addAbility(new DuplicantAbility0(state));

		// As long as the exiled card is a creature card, Duplicant has that
		// card's power, toughness, and creature types. It's still a
		// Shapeshifter.
		this.addAbility(new DuplicantAbility1(state));
	}
}
