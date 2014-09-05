package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Angel of Serenity")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WWW")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class AngelofSerenity extends Card
{
	public static final class AngelofSerenityAbility1 extends EventTriggeredAbility
	{
		public AngelofSerenityAbility1(GameState state)
		{
			super(state, "When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator otherCreaturesOnBattlefield = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator creatureCardsInGraveyards = Intersect.instance(InZone.instance(GraveyardOf.instance(Players.instance())), HasType.instance(Type.CREATURE));
			Target target = this.addTarget(Union.instance(otherCreaturesOnBattlefield, creatureCardsInGraveyards), "up to three other target creatures from the battlefield and/or creature cards from graveyards.");

			EventFactory exile = exile(targetedBy(target), "Exile up to three other target creatures from the battlefield and/or creature cards from graveyards.");
			exile.setLink(this);
			this.addEffect(youMay(exile));

			this.getLinkManager().addLinkClass(AngelofSerenityAbility2.class);
		}
	}

	public static final class AngelofSerenityAbility2 extends EventTriggeredAbility
	{
		public AngelofSerenityAbility2(GameState state)
		{
			super(state, "When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiled = ChosenFor.instance(LinkedTo.instance(This.instance()));
			this.addEffect(bounce(exiled, "Return the exiled cards to their owners' hands."));

			this.getLinkManager().addLinkClass(AngelofSerenityAbility1.class);
		}
	}

	public AngelofSerenity(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Angel of Serenity enters the battlefield, you may exile up to
		// three other target creatures from the battlefield and/or creature
		// cards from graveyards.
		this.addAbility(new AngelofSerenityAbility1(state));

		// When Angel of Serenity leaves the battlefield, return the exiled
		// cards to their owners' hands.
		this.addAbility(new AngelofSerenityAbility2(state));
	}
}
