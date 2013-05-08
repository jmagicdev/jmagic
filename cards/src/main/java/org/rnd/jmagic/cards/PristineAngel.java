package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pristine Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class PristineAngel extends Card
{
	public static final class Protection extends org.rnd.jmagic.abilities.keywords.Protection
	{
		private static SetGenerator from()
		{
			return Union.instance(HasType.instance(Type.ARTIFACT), HasColor.instance(Identity.instance(Color.allColors())));
		}

		public Protection(GameState state)
		{
			super(state, from(), "artifacts and from all colors");
		}
	}

	public static final class UntappedIsProtected extends StaticAbility
	{
		public UntappedIsProtected(GameState state)
		{
			super(state, "As long as Pristine Angel is untapped, it has protection from artifacts and from all colors.");
			this.canApply = Both.instance(this.canApply, Intersect.instance(This.instance(), Untapped.instance()));
			this.addEffectPart(addAbilityToObject(This.instance(), Protection.class));
		}
	}

	public static final class CastToProtect extends EventTriggeredAbility
	{
		public CastToProtect(GameState state)
		{
			super(state, "Whenever you cast a spell, you may untap Pristine Angel.");

			this.addPattern(whenYouCastASpell());

			EventFactory untapThis = untap(ABILITY_SOURCE_OF_THIS, "Untap Pristine Angel");
			this.addEffect(youMay(untapThis, "You may untap Pristine Angel."));
		}
	}

	public PristineAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// As long as Pristine Angel is untapped, it has protection from
		// artifacts and from all colors.
		this.addAbility(new UntappedIsProtected(state));

		// Whenever you cast a spell, you may untap Pristine Angel.
		this.addAbility(new CastToProtect(state));
	}
}
