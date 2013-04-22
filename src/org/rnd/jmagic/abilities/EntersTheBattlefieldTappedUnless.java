package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EntersTheBattlefieldTappedUnless extends StaticAbility
{
	private String permanentName;
	private SetGenerator unless;
	private String unlessText;

	public EntersTheBattlefieldTappedUnless(GameState state, String permanentName, SetGenerator unless, String unlessText)
	{
		super(state, permanentName + " enters the battlefield tapped unless " + unlessText + ".");
		this.permanentName = permanentName;
		this.unless = unless;
		this.unlessText = unlessText;

		ZoneChangeReplacementEffect tapped = new ZoneChangeReplacementEffect(this.game, permanentName + " enters the battlefield tapped");
		tapped.addPattern(asThisEntersTheBattlefield());
		tapped.addEffect(tap(NewObjectOf.instance(tapped.replacedByThis()), permanentName + " enters the battlefield tapped"));

		this.addEffectPart(replacementEffectPart(tapped));

		this.canApply = Not.instance(unless);
	}

	@Override
	public EntersTheBattlefieldTappedUnless create(Game game)
	{
		return new EntersTheBattlefieldTappedUnless(game.physicalState, this.permanentName, this.unless, this.unlessText);
	}
}
