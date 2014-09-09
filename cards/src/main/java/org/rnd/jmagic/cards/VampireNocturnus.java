package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vampire Nocturnus")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1BBB")
@ColorIdentity({Color.BLACK})
public final class VampireNocturnus extends Card
{
	public static final class Pump extends StaticAbility
	{
		public Pump(GameState state)
		{
			super(state, "As long as the top card of your library is black, Vampire Nocturnus and other Vampire creatures you control get +2/+1 and have flying.");

			SetGenerator topIsBlack = Intersect.instance(Identity.instance(Color.BLACK), ColorsOf.instance(TopCards.instance(1, LibraryOf.instance(You.instance()))));
			SetGenerator affectedCreatures = Union.instance(This.instance(), Intersect.instance(CreaturePermanents.instance(), Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance()))));

			this.addEffectPart(modifyPowerAndToughness(affectedCreatures, 2, 1));

			this.addEffectPart(addAbilityToObject(affectedCreatures, org.rnd.jmagic.abilities.keywords.Flying.class));

			this.canApply = Both.instance(this.canApply, topIsBlack);
		}
	}

	public VampireNocturnus(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.RevealTopOfLibrary(state));
		this.addAbility(new Pump(state));
	}
}
