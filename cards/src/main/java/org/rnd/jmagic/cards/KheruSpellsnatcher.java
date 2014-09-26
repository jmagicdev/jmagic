package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kheru Spellsnatcher")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.NAGA})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class KheruSpellsnatcher extends Card
{
	public static final class KheruSpellsnatcherAbility1 extends EventTriggeredAbility
	{
		public KheruSpellsnatcherAbility1(GameState state)
		{
			super(state, "When Kheru Spellsnatcher is turned face up, counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may cast that card without paying its mana cost for as long as it remains exiled.");
			this.addPattern(whenThisIsTurnedFaceUp());

			SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
			EventFactory counter = counter(target, "Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.");
			counter.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			this.addEffect(counter);

			PlayPermission permission = new PlayPermission(You.instance());
			permission.forceAlternateCost(Empty.instance());

			ContinuousEffect.Part mayCast = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_LOCATION);
			mayCast.parameters.put(ContinuousEffectType.Parameter.OBJECT, EffectResult.instance(counter));
			mayCast.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
			this.addEffect(createFloatingEffect(Empty.instance(), "You may cast that card without paying its mana cost for as long as it remains exiled.", mayCast));
		}
	}

	public KheruSpellsnatcher(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Morph (4)(U)(U) (You may cast this card face down as a 2/2 creature
		// for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(U)(U)"));

		// When Kheru Spellsnatcher is turned face up, counter target spell. If
		// that spell is countered this way, exile it instead of putting it into
		// its owner's graveyard. You may cast that card without paying its mana
		// cost for as long as it remains exiled.
		this.addAbility(new KheruSpellsnatcherAbility1(state));
	}
}
