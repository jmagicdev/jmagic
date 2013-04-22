package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snapcaster Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SnapcasterMage extends Card
{
	public static final class SnapcasterMageAbility1 extends EventTriggeredAbility
	{
		public SnapcasterMageAbility1(GameState state)
		{
			super(state, "When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))), "target instant or sorcery card in your graveyard"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.GRANT_COSTED_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flashback.class));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			this.addEffect(createFloatingEffect("Target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.", part));
		}
	}

	public SnapcasterMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Snapcaster Mage enters the battlefield, target instant or
		// sorcery card in your graveyard gains flashback until end of turn. The
		// flashback cost is equal to its mana cost. (You may cast that card
		// from your graveyard for its flashback cost. Then exile it.)
		this.addAbility(new SnapcasterMageAbility1(state));
	}
}
