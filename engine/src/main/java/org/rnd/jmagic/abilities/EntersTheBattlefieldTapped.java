package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EntersTheBattlefieldTapped extends StaticAbility
{
	private String permanentName;

	public EntersTheBattlefieldTapped(GameState state, String permanentName)
	{
		super(state, permanentName + " enters the battlefield tapped.");
		this.permanentName = permanentName;

		ZoneChangeReplacementEffect tapped = new ZoneChangeReplacementEffect(this.game, permanentName + " enters the battlefield tapped");
		tapped.addPattern(asThisEntersTheBattlefield());

		tapped.addEffect(tap(NewObjectOf.instance(tapped.replacedByThis()), permanentName + " enters the battlefield tapped"));
		this.addEffectPart(replacementEffectPart(tapped));
		this.canApply = NonEmpty.instance();
	}

	@Override
	public EntersTheBattlefieldTapped create(Game game)
	{
		return new EntersTheBattlefieldTapped(game.physicalState, this.permanentName);
	}
}
