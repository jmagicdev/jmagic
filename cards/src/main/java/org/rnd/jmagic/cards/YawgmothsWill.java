package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Yawgmoth's Will")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class YawgmothsWill extends Card
{
	public YawgmothsWill(GameState state)
	{
		super(state);

		// Until end of turn, you may play cards from your graveyard.
		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		ContinuousEffect.Part playPart = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
		playPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, InZone.instance(yourGraveyard));
		playPart.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
		this.addEffect(createFloatingEffect("Until end of turn, you may play cards from your graveyard.", playPart));

		// If a card would be put into your graveyard from anywhere this turn,
		// exile that card instead.
		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If a card would be put into your graveyard from anywhere this turn, exile that card instead.");
		replacement.addPattern(new SimpleZoneChangePattern(null, yourGraveyard, Cards.instance(), true));
		replacement.changeDestination(ExileZone.instance());
		this.addEffect(createFloatingReplacement(replacement, "If a card would be put into your graveyard from anywhere this turn, exile that card instead."));
	}
}
